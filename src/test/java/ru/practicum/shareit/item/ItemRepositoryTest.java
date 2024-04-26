package ru.practicum.shareit.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        },
        showSql = true)
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private Item itemFirst;
    private Item itemSecond;

    @BeforeEach
    public void setup() {
        user = userRepository.save(new User(33L, "Petia", "123456newemail@mail.com"));
        itemFirst = itemRepository.save(
                new Item(0L, "Дрель", "Аккумуляторная дрель", true, user, null));
        itemSecond = itemRepository.save(
                new Item(0L, "Дрель", "Простая дрель", true, user, null));
    }

    @Test
    public void searchTest() {
        int from = 0;
        int size = 2;
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        String text = "аккУМУляторная";
        Assertions.assertEquals(1, itemRepository.search(text, page).size());
    }

    @AfterEach
    public void clear() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}
