package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.booking.BookingRepository;
import ru.practicum.booking.Status;
import ru.practicum.booking.dto.BookingInfo;
import ru.practicum.booking.model.Booking;
import ru.practicum.comment.CommentMapper;
import ru.practicum.comment.CommentRepository;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.exception.NotCorrectDataException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.item.dto.ItemDatesDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemMapper;
import ru.practicum.item.dto.ItemWithCommentsDto;
import ru.practicum.item.model.Item;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;

    @Override
    public ItemDto addItem(long userId, ItemDto itemDto) {
        existsUser(userId);
        User owner = userRepository.getById(userId);
        itemDto.setOwnerId(userId);
        ItemRequest request = null;

        if (itemDto.getRequestId() != null) {
             request = requestRepository.getById(itemDto.getRequestId());
        }
        return ItemMapper.convertItemToDto(itemRepository.save(ItemMapper.convertDtoToItem(itemDto, owner, request )));
    }

    @Override
    public List<ItemDatesDto> getUserItems(long userId) {
        existsUser(userId);
        List<ItemDatesDto> itemDatesDtoList = new ArrayList<>();
        List<Item> usersItems = itemRepository.getByOwner_id(userId);
        usersItems.forEach(i -> itemDatesDtoList.add(makeItemDatesDto(i.getId(), userId)));
        return itemDatesDtoList;
    }

    @Override
    public ItemWithCommentsDto getItemById(long itemId, long userId) {
        existsItem(itemId);
        existsUser(userId);
        ItemDatesDto itemDatesDto = makeItemDatesDto(itemId, userId);
        return ItemMapper.convertToItemWithCommentsDto(
                itemDatesDto,
                commentRepository.findByItem_Id(itemId).stream()
                        .map(CommentMapper::convertToCommentsAuthorNameDto)
                        .collect(Collectors.toList()));
    }

    private ItemDatesDto makeItemDatesDto(long itemId, long userId) {
        LocalDateTime NOW_TIME = LocalDateTime.now();
        Item item = itemRepository.getById(itemId);
        long ownerId = item.getOwner().getId();
        ItemDatesDto itemDatesDto = ItemMapper.convertItemToItemDatesDto(item, null, null);
        if (userId == ownerId) {
            List<BookingInfo> bookingList = bookingRepository.findByItem_Id(itemId).stream()
                    .sorted(Comparator.comparing(BookingInfo::getStart).reversed()).collect(Collectors.toList());
            if (!bookingList.isEmpty()) {
                Optional<BookingInfo> lastBookingOpt = bookingList.stream()
                        .sorted(Comparator.comparing(BookingInfo::getStart).reversed())
                        .filter(b -> b.getStart().isBefore(NOW_TIME) && bookingRepository.getById(b.getId()).getStatus() == Status.APPROVED)
                        .findFirst();
                lastBookingOpt.ifPresent(itemDatesDto::setLastBooking);
                bookingList = bookingRepository.findByItem_Id(itemId).stream()
                        .sorted(Comparator.comparing(BookingInfo::getEnd)).distinct().collect(Collectors.toList());
                Optional<BookingInfo> nextBookingOpt = bookingList.stream()
                        .sorted(Comparator.comparing(BookingInfo::getEnd))
                        .filter(b -> b.getStart().isAfter(NOW_TIME) && bookingRepository.getById(b.getId()).getStatus() == Status.APPROVED)
                        .findFirst();
                nextBookingOpt.ifPresent(itemDatesDto::setNextBooking);
            }
        }
        return itemDatesDto;
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        existsItem(itemId);
        Item oldItem = itemRepository.getById(itemId);
        long trueOwnerId = oldItem.getOwner().getId();
        if (trueOwnerId != userId) {
            throw new NotFoundException("Попытка обновить собственника вещи.");
        }
        if (itemDto.getAvailable() != null) {
            oldItem.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getDescription() != null) {
            oldItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getName() != null) {
            oldItem.setName(itemDto.getName());
        }
        Item updateItem = itemRepository.save(oldItem);
        return ItemMapper.convertItemToDto(updateItem);
    }

    @Override
    public List<ItemDto> search(long userId, String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        } else {
            return itemRepository.search(text).stream()
                    .map(ItemMapper::convertItemToDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, long authorId, Long itemId) {
        existsUser(authorId);
        existsItem(itemId);
        User author = userRepository.getById(authorId);
        Item item = itemRepository.getById(itemId);
        List<Booking> authorBookings = bookingRepository.findByBooker_Id(authorId);
        Optional<Booking> booking = authorBookings.stream()
                .findFirst().filter(b -> b.getBooker().getId() == authorId
                        && !b.getStatus().equals(Status.REJECTED)
                        && !b.getStart().isAfter(LocalDateTime.now()));
        if (booking.isEmpty()) {
            throw new NotCorrectDataException("Автор не брал вещь в аренду!");
        }
        return CommentMapper.convertCommentToDto(
                commentRepository.save(CommentMapper.convertToComment(commentDto, item, author)));
    }

    @Override
    public void deleteItem(long itemId) {
        itemRepository.deleteById(itemId);
    }

    private void existsUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользоватеь с id = " + userId + " не найден.");
        }
    }

    private void existsItem(long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException("Вещь с id = " + itemId + "не найдена.");
        }
    }
}
