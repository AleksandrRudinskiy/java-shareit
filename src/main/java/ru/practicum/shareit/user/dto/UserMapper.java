package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserMapper {

    public static UserDto convertUserToDto(User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User convertDtotoUser(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }
}
