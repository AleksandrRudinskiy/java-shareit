package ru.practicum.user;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto add(UserDto userDto);

    List<UserDto> getUsers();

    UserDto update(long userId, UserDto user);

    UserDto getUserById(long userId);

    void deleteUser(long userId);
}
