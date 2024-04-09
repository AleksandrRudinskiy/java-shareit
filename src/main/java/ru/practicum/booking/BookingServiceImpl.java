package ru.practicum.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingFullDto;
import ru.practicum.booking.dto.BookingMapper;
import ru.practicum.booking.model.Booking;
import ru.practicum.exception.*;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserInfo;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingFullDto addBooking(long userId, BookingDto bookingDto) {
        validate(bookingDto);
        existsUser(userId);
        long itemId = bookingDto.getItemId();
        existsItem(itemId);
        Item item = itemRepository.getById(itemId);
        long ownerId = item.getOwner().getId();
        if (userId == ownerId) {
            throw new NotFoundException("Owner не может быть booker!");
        }
        if (!item.getAvailable()) {
            throw new NotAvailableException("Вещь с itemId = " + itemId + " не доступна для аренды.");
        }
        bookingDto.setBookerId(userId);
        bookingDto.setStatus(Status.WAITING);
        User booker = userRepository.getById(userId);
        Booking booking = bookingRepository.save(BookingMapper.convertDtoToBooking(bookingDto, booker, item));
        ItemInfo itemInfo = itemRepository.findAllById(itemId);
        UserInfo userInfo = userRepository.getAllById(userId);
        return BookingMapper.convertToFullDto(booking, itemInfo, userInfo);
    }

    @Override
    public BookingFullDto update(long bookingId, long userId, Boolean approved, BookingDto bookingDto) {
        existsUser(userId);
        existsBooking(bookingId);
        Booking oldBooking = bookingRepository.getById(bookingId);
        long bookerId = oldBooking.getBooker().getId();
        long itemId = oldBooking.getItem().getId();
        Item item = itemRepository.getById(itemId);
        long ownerId = item.getOwner().getId();
        if (oldBooking.getStatus().equals(Status.APPROVED) && userId == ownerId) {
            throw new NotAvailableException("Нельзя обновить статус после approved");
        }
        if (userId == bookerId && approved) {
            throw new NotFoundException("Обновить статус booker не может");
        }
        if (userId != ownerId) {
            throw new NotOwnerException("Обновить статус может только собственник!");
        }
        if (approved) {
            oldBooking.setStatus(Status.APPROVED);
        }
        if (!approved) {
            oldBooking.setStatus(Status.REJECTED);
        }
        Booking patchBooking = bookingRepository.save(oldBooking);
        ItemInfo itemInfo = itemRepository.findAllById(item.getId());
        UserInfo userInfo = userRepository.getAllById(bookerId);
        return BookingMapper.convertToFullDto(patchBooking, itemInfo, userInfo);
    }

    @Override
    public BookingFullDto getBookingById(long bookingId, long userId) {
        existsUser(userId);
        existsBooking(bookingId);
        Booking booking = bookingRepository.getById(bookingId);

        long bookerId = booking.getBooker().getId();
        Long itemId = booking.getItem().getId();
        Item item = itemRepository.getById(itemId);
        long ownerId = item.getOwner().getId();

        if (userId != bookerId && userId != ownerId) {
            throw new NotFoundException("Посмотреть бронирование может только owner или booker!");
        }
        ItemInfo itemInfo = itemRepository.findAllById(itemId);
        UserInfo userInfo = userRepository.getAllById(bookerId);
        return BookingMapper.convertToFullDto(booking, itemInfo, userInfo);
    }

    private void existsUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с userId = " + userId + " не найден.");
        }
    }

    private void existsBooking(long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new NotFoundException("Бронирование с id = " + bookingId + " не найдено.");
        }
    }

    private void existsItem(long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException("Вещь с itemId = " + itemId + " не найдена.");
        }
    }

    private void validate(BookingDto bookingDto) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd())
                || bookingDto.getStart().isEqual(bookingDto.getEnd())
                || bookingDto.getStart().isBefore(LocalDateTime.now())
                || bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new NotCorrectDataException("Даты бронирования указаны не верно/не указаны.");
        }
    }

    @Override
    public List<BookingFullDto> getOwnerBookings(long userId, String state) {
        existsUser(userId);
        return applyState(convertToFullDtoList(bookingRepository.getOwnerBookings(userId)), state);
    }

    @Override
    public List<BookingFullDto> getBookerBookings(long userId, String state) {
        existsUser(userId);
        return applyState(convertToFullDtoList(bookingRepository.getBookerBookings(userId)), state);
    }

    private List<BookingFullDto> convertToFullDtoList(List<Booking> bookingList) {
        List<BookingFullDto> bookingFullDtos = new ArrayList<>();
        for (Booking booking : bookingList) {
            long itemId = booking.getItem().getId();
            ItemInfo itemInfo = itemRepository.findAllById(itemId);
            long bookerId = booking.getBooker().getId();
            UserInfo userInfo = userRepository.getAllById(bookerId);
            BookingFullDto bookingFullDto = BookingMapper.convertToFullDto(booking, itemInfo, userInfo);
            bookingFullDtos.add(bookingFullDto);
        }
        return bookingFullDtos;
    }

    private List<BookingFullDto> applyState(List<BookingFullDto> bookingFullDtos, String state) {
        switch (State.valueOf(state)) {
            case CURRENT:
                return bookingFullDtos.stream()
                        .filter(b -> b.getEnd().isAfter(LocalDateTime.now()) && b.getStart().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case PAST:
                return bookingFullDtos.stream()
                        .filter(b -> b.getEnd().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingFullDtos.stream()
                        .filter(b -> b.getStart().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case WAITING:
                return bookingFullDtos.stream()
                        .filter(b -> b.getStatus().equals(Status.WAITING))
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingFullDtos.stream()
                        .filter(b -> b.getStatus().equals(Status.REJECTED))
                        .collect(Collectors.toList());
            case ALL:
                return bookingFullDtos;
            case UNSUPPORTED_STATUS:
                throw new NotSupportedStateException("Unknown state: UNSUPPORTED_STATUS");

        }
        return bookingFullDtos;
    }
}
