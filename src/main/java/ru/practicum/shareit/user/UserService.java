package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(UserDto dto) {
        User user = UserMapper.toUser(dto);
        User createdUser = userRepository.createUser(user);

        return UserMapper.toUserDto(createdUser);
    }

    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAllUsers();

        return userList.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        return UserMapper.toUserDto(user);
    }

    public UserDto updateUser(Long id, UserDto dto) {
        User user = UserMapper.toUser(dto);
        User updatedUser = userRepository.updateUser(id,user);

        return UserMapper.toUserDto(updatedUser);
    }

    public void removeUser(Long id) {
        userRepository.removeUser(id);
    }
}
