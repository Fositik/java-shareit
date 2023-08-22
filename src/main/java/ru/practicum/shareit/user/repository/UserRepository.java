package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user);

    List<User> findAllUsers();

    User findUserById(Long id);

    User updateUser(Long id, User user);

    void removeUser(Long id);
}