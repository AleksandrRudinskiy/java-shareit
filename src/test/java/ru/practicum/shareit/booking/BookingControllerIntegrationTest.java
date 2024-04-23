package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ShareItApp;
import ru.practicum.booking.BookingRepository;
import ru.practicum.booking.Status;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingFullDto;
import ru.practicum.booking.dto.BookingMapper;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserInfo;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShareItApp.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")

@Slf4j
public class BookingControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RequestRepository requestRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void postBookingThenStatus200() throws Exception {
        User user = userRepository.save(new User(1L, "Petia", "newemail@mail.com"));
        User requestor = userRepository.save(new User(2L, "Kolya", "mail@mail.com"));

        ItemRequest request = requestRepository.save(
                new ItemRequest(1L, "нужна дрель", user, LocalDateTime.now()));

        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, user, request));

        Booking booking =
                new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(1), item,
                        requestor, Status.WAITING);

        ItemInfo itemInfo = itemRepository.findAllById(item.getId());
        UserInfo userInfo = userRepository.getAllById(requestor.getId());

        BookingFullDto bookingFullDto = BookingMapper.convertToFullDto(booking, itemInfo, userInfo);

        BookingDto bookingDto = BookingMapper.convertToBookingDto(bookingRepository.save(booking), item);

        log.info("BookingFullDto = {}", bookingFullDto);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 2)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getBookingById1ThenStatus200() throws Exception {
        User user = userRepository.save(new User(1L, "Petia", "newemail@mail.com"));
        User requestor = userRepository.save(new User(2L, "Kolya", "mail@mail.com"));

        ItemRequest request = requestRepository.save(
                new ItemRequest(1L, "нужна дрель", user, LocalDateTime.now()));

        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, user, request));

        Booking booking = bookingRepository.save(
                new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(1), item,
                        requestor, Status.WAITING));

        BookingFullDto bookingFullDto = BookingMapper.convertToFullDto(
                booking, itemRepository.findAllById(item.getId()), userRepository.getAllById(requestor.getId()));

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void patchBookingThenStatus200() throws Exception {
        User user = userRepository.save(new User(1L, "Petia", "newemail@mail.com"));
        User requestor = userRepository.save(new User(2L, "Kolya", "mail@mail.com"));

        ItemRequest request = requestRepository.save(
                new ItemRequest(1L, "нужна дрель", user, LocalDateTime.now()));

        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, user, request));

        Booking booking = bookingRepository.save(
                new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(1), item,
                        requestor, Status.WAITING));

        BookingDto bookingDto = BookingMapper.convertToBookingDto(
                new Booking(1L, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(2), item,
                        requestor, Status.WAITING), item);


        mvc.perform(patch("/bookings/1")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", 1)
                        .param("approved", String.valueOf(true))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
