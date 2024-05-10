package ru.practicum.shareit.booking;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.booking.BookingRepository;
import ru.practicum.booking.BookingService;
import ru.practicum.booking.BookingServiceImpl;
import ru.practicum.item.ItemRepository;
import ru.practicum.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    private final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final BookingService bookingService = new BookingServiceImpl(bookingRepository, userRepository, itemRepository);

    @Test
    public void getBookingByIdWhenNotExists() {

    }
}
