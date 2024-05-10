package ru.practicum.shareit.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.UserRepository;
import ru.practicum.user.UserService;
import ru.practicum.user.UserServiceImpl;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;
import ru.practicum.user.model.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final UserDto userDto = new UserDto(
            1L,
            "John",
            "john.doe@mail.com");

    private final User newUser = new User(
            1L,
            "NewJohn",
            "john.doe@mail.com");

    @Test
    public void updateUserTest() {
        User user = new User(
                1L,
                "John",
                "john.doe@mail.com");

        when(userRepository.existsById(Mockito.anyLong()))
                .thenReturn(true);
        when(userRepository.getById(anyLong()))
                .thenReturn(user);
        when(userRepository.save(any()))
                .thenReturn(newUser);

        UserDto newUserDto = UserMapper.convertUserToDto(newUser);
        UserDto updateUserDto = userService.update(1L, newUserDto);
        Assertions.assertEquals(1L, userDto.getId());
        Assertions.assertEquals("NewJohn", updateUserDto.getName());
    }

    @Test
    public void updateUserByNotFoundIdTest() throws Exception {
        UserDto newUserDto = UserMapper.convertUserToDto(newUser);
        when(userRepository.existsById(Mockito.anyLong()))
                .thenReturn(false);
        final NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.update(1L, newUserDto));
        Assertions.assertEquals("Пользователь с id = " + newUser.getId() + " не существует.",
                exception.getMessage());
    }


}
