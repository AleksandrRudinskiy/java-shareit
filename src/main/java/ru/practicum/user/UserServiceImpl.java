package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.DuplicateUserEmailException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.WrongFormatUserEmailException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;
import ru.practicum.user.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto add(UserDto userDto) {
        validateUser(UserMapper.convertDtoToUser(userDto));
        return UserMapper.convertUserToDto(userRepository.save(UserMapper.convertDtoToUser(userDto)));
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertUserToDto).collect(Collectors.toList());
    }

    @Override

    public UserDto update(long userId, UserDto userDto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
        User oldUser = userRepository.getById(userId);
        if (userDto.getName() != null) {
            oldUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            Optional<User> user = userRepository.findAll().stream().filter(u -> u.getEmail().contains(userDto.getEmail()) && u.getId() != userId).findFirst();
            if (user.isPresent()) {
                throw new DuplicateUserEmailException("Указанный email " + userDto.getEmail() + " уже существует.");
            }
            oldUser.setEmail(userDto.getEmail());
        }
        User patchUser = userRepository.save(oldUser);
        return UserMapper.convertUserToDto(patchUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
        return UserMapper.convertUserToDto(userRepository.getById(userId));
    }

    @Override
    public void deleteUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
        userRepository.deleteById(userId);
    }

    private void validateUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new WrongFormatUserEmailException("Email должен содержать символ \"@\".");
        }
    }
}
