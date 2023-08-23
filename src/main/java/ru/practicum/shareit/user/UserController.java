package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.utils.validation.Create;
import ru.practicum.shareit.utils.validation.Update;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("Request received to /users create endpoint");
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAll() {
        log.info("Endpoint request received: /users getAll");
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long userId) {
        log.info("Request received to endpoint: /users getById with id={}", userId);
        return userService.findUserById(userId);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable("id") Long userId,
                          @Validated(Update.class)
                          @RequestBody UserDto userDto) {
        log.info("Request received to endpoint: /users update with id={}", userId);
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long userId) {
        log.info("Request received to endpoint: /users delete with id={}", userId);
        userService.removeUser(userId);
    }
}
