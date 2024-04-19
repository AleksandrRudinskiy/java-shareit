package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {

    User add(User user);

    List<User> getUsers();

    User update(long userId, User user);

    User getUserById(long userId);

    void deleteUser(long userId);
}
