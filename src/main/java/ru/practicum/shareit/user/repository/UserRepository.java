package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;

/**
 * Класс описывает interface UserRepository хранение в базе данных
 */

public interface UserRepository {
    User createUser(User user);

    List<User> findAllUsers();

    User findUserById(Long id);

    User updateUser(Long id, User user);

    void removeUser(Long id);
}