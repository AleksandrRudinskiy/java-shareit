package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.booking.Status;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingMapper;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
@RunWith(SpringRunner.class)
public class BookingMapperDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBookingDtoFormat() throws Exception {
        User user = new User(1L, "Petia", "newemail@mail.com");
        User requestor = new User(2L, "Kolya", "mail@mail.com");
        ItemRequest request = new ItemRequest(1L, "нужна дрель", user, LocalDateTime.now());
        Item item = new Item(1L, "Дрель", "Простая дрель", true, user, request);
        Booking booking = new Booking(
                1L, LocalDateTime.of(2024, 05, 20, 12, 30, 20),
                LocalDateTime.of(2024, 05, 21, 12, 30, 20),
                item, requestor, Status.WAITING);

        BookingDto bookingDto = BookingMapper.convertToBookingDto(booking, item);
        String expectedResult = "{\"id\":1," +
                "\"start\":\"2024-05-20T12:30:20\"," +
                "\"end\":\"2024-05-21T12:30:20\",\"itemId\":1,\"itemName\":\"Дрель\"," +
                "\"status\":\"WAITING\",\"booker\":2}";

        assertEquals(expectedResult, objectMapper.writeValueAsString(bookingDto));
    }

}
