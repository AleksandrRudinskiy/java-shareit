package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.DuplicateUserEmailException;
import ru.practicum.shareit.user.exception.NoUserEmailException;
import ru.practicum.shareit.user.exception.NotFoundException;
import ru.practicum.shareit.user.exception.WrongFormatUserEmailException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        validateUser(user);
        userRepository.add(user);
        return user;
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(UserMapper::convertUserToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto update(long userId, UserDto userDto) {
        UserDto oldUserDto = getUserById(userId);
        if (userDto.getName() == null) {
            if (getUsers().stream().anyMatch(u -> u.getEmail().equals(userDto.getEmail()) && userId != u.getId())) {
                throw new DuplicateUserEmailException("Указанный email " + userDto.getEmail() + " уже существует.");
            }
            userDto.setName(oldUserDto.getName());
        }
        if (userDto.getEmail() == null) {
            userDto.setEmail(oldUserDto.getEmail());
        }
        return UserMapper.convertUserToDto(userRepository.update(userId, UserMapper.convertDtotoUser(userDto)));
    }

    @Override
    public UserDto getUserById(long userId) {
        return UserMapper.convertUserToDto(userRepository.getUserById(checkExists(userId)));
    }

    @Override
    public long checkExists(long userId) {
        if (userRepository.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        } else {
            return userId;
        }
    }

    @Override
    public void deleteUser(long userId) {
        checkExists(userId);
        userRepository.deleteUser(userId);
    }

    private void validateUser(User user) {
        if (getUsers().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && user.getId() != u.getId())) {
            throw new DuplicateUserEmailException("Указанный email " + user.getEmail() + " уже существует.");
        }
        if (user.getEmail() == null) {
            throw new NoUserEmailException("Не указан email пользователя.");
        }
        if (!user.getEmail().contains("@")) {
            throw new WrongFormatUserEmailException("Email должен содержать символ \"@\".");
        }
    }
}
