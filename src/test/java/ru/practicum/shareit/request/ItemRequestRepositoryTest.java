package ru.practicum.shareit.request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        },
        showSql = true)
public class ItemRequestRepositoryTest {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private User requestor;
    private ItemRequest firstItemRequest;

    @BeforeEach
    public void setup() {
        user = new User(1L, "Petia", "newemail@mail.com");
        userRepository.save(user);
        requestor = new User(2L, "Kolya", "mail@mail.com");
        userRepository.save(requestor);
        firstItemRequest = new ItemRequest(1L, "нужна дрель",
                user, LocalDateTime.of(2026, 4, 25, 10, 8, 54));
        firstItemRequest = requestRepository.save(firstItemRequest);
    }

    @Test
    public void findAllRequestsWithNotRequestorIdTest() {
        int from = 0;
        int size = 2;
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Assertions.assertEquals(firstItemRequest,
                requestRepository.findAllNotRequestorId(requestor.getId(), page).get(0),
                "Первый запрос в списке findAllNotRequestorId()  не верный!");
    }

    @AfterEach
    public void clear() {
        requestRepository.deleteAll();
        userRepository.deleteAll();
    }
}
