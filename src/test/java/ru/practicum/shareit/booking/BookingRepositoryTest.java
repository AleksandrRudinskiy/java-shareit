package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.booking.BookingRepository;
import ru.practicum.booking.Status;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        },
        showSql = true)
@Slf4j
public class BookingRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;
    private Booking booking;
    private User owner;
    private User booker;

    @BeforeEach
    public void setup() {
        owner = userRepository.save(new User(1L, "Petia", "newemail@mail.com"));
        booker = userRepository.save(new User(5L, "Kolya", "mailmail@mail.com"));
        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, owner, null));
        booking = bookingRepository.save(
                new Booking(1L,
                        LocalDateTime.of(2025, 6, 5, 12, 15, 0),
                        LocalDateTime.of(2026, 6, 5, 12, 15, 0),
                        item,
                        booker,
                        Status.WAITING));
    }

    @Test
    public void getBookingsByBookerIdTest() {
        PageRequest page = PageRequest.of(0, 2);
        assertEquals(List.of(booking), bookingRepository.getByBookerId(booker.getId(), page));
    }

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
        bookingRepository.deleteAll();
        itemRepository.deleteAll();
    }
}
