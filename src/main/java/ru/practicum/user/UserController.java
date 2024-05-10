package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> get() {
        log.info("GET-запрос на получение всех пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        log.info("GET-запрос на получение пользователя по id {}", userId);
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto add(@RequestBody @Valid UserDto userDto) {
        log.info("POST-запрос на добавление пользователя");
        return userService.add(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        log.info("PATCH-запрос на обновление пользователя");
        return userService.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable long userId) {
        log.info("DELETE-запрос на удаление пользователя с id {}", userId);
        userService.deleteUser(userId);
    }
}