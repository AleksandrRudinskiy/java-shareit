package ru.practicum.shareit.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
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
import ru.practicum.booking.model.Booking;
import ru.practicum.comment.CommentMapper;
import ru.practicum.comment.CommentRepository;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShareItApp.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CommentControllerIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    CommentRepository commentRepository;
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

    @Test
    public void postCommentThenStatus200() throws Exception {
        User user = userRepository.save(new User(1L, "Petia", "newemail@mail.com"));
        User requestor = userRepository.save(new User(2L, "Kolya", "mail@mail.com"));
        ItemRequest request = requestRepository.save(
                new ItemRequest(1L, "нужна дрель", requestor, LocalDateTime.now()));
        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, user, request));
        Booking booking = bookingRepository.save(
                new Booking(1L, LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(5), item,
                        requestor, Status.APPROVED));

        Comment comment = new Comment(
                1L, "Хорошая вещь", item, requestor, null);

        CommentDto commentDto = CommentMapper.convertCommentToDto(comment);

        mvc.perform(post("/items/1/comment")
                        .content(mapper.writeValueAsString(commentDto))
                        .header("X-Sharer-User-Id", 2)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(commentDto.getText())));
    }

    @AfterEach
    public void clear() {
        itemRepository.deleteAll();
        requestRepository.deleteAll();
        userRepository.deleteAll();
    }


}
