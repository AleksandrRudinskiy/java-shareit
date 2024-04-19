package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users;
    private long currentId;

    @Override
    public User add(User user) {
        long id = generateId();
        user.setId(id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User update(long userId, User user) {
        user.setId(userId);
        users.put(userId, user);
        return users.get(userId);
    }

    @Override
    public User getUserById(long userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

    private Long generateId() {
        currentId++;
        return currentId;
    }
}
