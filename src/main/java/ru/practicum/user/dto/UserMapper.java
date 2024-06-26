package ru.practicum.user.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.user.model.User;

@UtilityClass
public class UserMapper {

    public static UserDto convertUserToDto(User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User convertDtoToUser(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }
}
