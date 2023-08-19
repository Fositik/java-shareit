package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.UserAlreadyExistException;
import ru.practicum.shareit.exceptions.UserIdValidationException;
import ru.practicum.shareit.user.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс описывает UserRepositoryInMemory хранение в памяти
 */

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final AtomicLong id = new AtomicLong(1L);
    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public User createUser(User user) {
        if (userMap.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException(String.format("User with this email: %s already exists",
                    user.getEmail()));
        }
        Long userId = id.incrementAndGet();

        user.setId(userId);
        userMap.put(user.getEmail(), user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findUserById(Long id) {
        return userMap.values().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserIdValidationException(String.format("User with id: %d not found", id)));
    }

    @Override
    public User updateUser(Long id, User user) {
        User updatedUser = userMap.values().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserIdValidationException(String.format("User with id: %d not found", id)));
        String updatedUserEmail = updatedUser.getEmail();

        if (userMap.containsKey(user.getEmail()) && !updatedUserEmail.equals(user.getEmail())) {
            throw new UserAlreadyExistException(String.format("User with this email: %s already exists",
                    user.getEmail()));
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()){
            updatedUser.setEmail(user.getEmail());
        }
        userMap.remove(updatedUserEmail);
        userMap.put(updatedUser.getEmail(), updatedUser);
        return updatedUser;
    }

    @Override
    public void removeUser(Long id) {
        User removedUser = userMap.values().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserIdValidationException(String.format("User with id: %d not found", id)));
        userMap.remove(removedUser.getEmail());
    }
}