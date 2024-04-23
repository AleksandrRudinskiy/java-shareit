package ru.practicum.shareit.booking;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.booking.BookingRepository;
import ru.practicum.booking.BookingService;
import ru.practicum.booking.BookingServiceImpl;
import ru.practicum.booking.Status;
import ru.practicum.booking.dto.BookingFullDto;
import ru.practicum.booking.model.Booking;
import ru.practicum.exception.NotFoundException;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserInfo;
import ru.practicum.user.UserRepository;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private BookingService bookingService = new BookingServiceImpl(bookingRepository, userRepository, itemRepository);

    @Test
    public void getBookingByIdWhenNotExists() {

    }
}
