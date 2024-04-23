package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.practicum.item.ItemRepository;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemMapper;
import ru.practicum.item.model.Item;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShareItApp.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void postItemThenStatus200() throws Exception {
        User user = userRepository.save(
                new User(1L, "Petia",
                        "newemail@mail.com"));

        User requestor = userRepository.save(
                new User(2L, "Kolya",
                        "mail@mail.com"));

        ItemRequest request = requestRepository.save(new ItemRequest(1L, "нужна дрель", requestor, LocalDateTime.now()));

        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, user, request));

        ItemDto itemDto = ItemMapper.convertItemToDto(item);
        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())));
    }
    @Test
    public void getItemById1ThenStatus200() throws Exception {
        User user = userRepository.save(
                new User(1L, "Petia",
                        "newemail@mail.com"));

        User requestor = userRepository.save(
                new User(2L, "Kolya",
                        "mail@mail.com"));

        ItemRequest request = requestRepository.save(new ItemRequest(1L, "нужна дрель", requestor, LocalDateTime.now()));

        Item item = itemRepository.save(
                new Item(1L, "Дрель", "Простая дрель", true, user, request));


        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Дрель")));
    }
}
