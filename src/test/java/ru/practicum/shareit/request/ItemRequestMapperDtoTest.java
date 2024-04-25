package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.request.dto.ItemRequestMapper;
import ru.practicum.request.dto.ItemRequestWithItemsDto;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
@RunWith(SpringRunner.class)
public class ItemRequestMapperDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testItemRequestDtoFormat() throws Exception {
        User user = new User(1L, "Petia", "newemail@mail.com");
        User requestor = new User(2L, "Kolya", "mail@mail.com");
        ItemRequest request = new ItemRequest(1L, "нужна дрель",
                user, LocalDateTime.of(2024, 4, 25, 10, 8, 54));
        ItemRequestWithItemsDto itemRequestWithItemsDto = ItemRequestMapper.convertToItemRequestWithItemsDto(request,
                null);
        String expectedResult = "{\"id\":1,\"description\":\"нужна дрель\"," +
                "\"requestorId\":1,\"created\":\"2024-04-25T10:08:54\",\"items\":null}";
        assertEquals(expectedResult, objectMapper.writeValueAsString(itemRequestWithItemsDto));
    }
}
