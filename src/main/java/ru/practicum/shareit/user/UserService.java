package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    User add(User user);

    List<UserDto> getUsers();

    UserDto update(long userId, UserDto user);

    UserDto getUserById(long userId);

    long checkExists(long userId);

    void deleteUser(long userId);
}
