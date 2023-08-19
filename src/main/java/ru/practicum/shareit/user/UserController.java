package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Validated
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto, BindingResult result) {
        log.info("Request received to /users create endpoint");
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("fieldName").getDefaultMessage();
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAll() {
        log.info("Endpoint request received: /users getAll");
        return userService.findAllUsers();
    }

    @GetMapping("/id")
    public UserDto getById(@PathVariable("id") Long userId) {
        log.info("Request received to endpoint: /users getById with id={}", userId);
        return userService.findUserById(userId);
    }

    @PatchMapping("/id")
    public UserDto update(@PathVariable("id") Long userId,
                          @RequestBody UserDto userDto,
                          BindingResult result) {
        log.info("Request received to endpoint: /users update with id={}", userId);
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("fieldName").getDefaultMessage();
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/id")
    public HttpStatus delete(@PathVariable("id") Long userId) {
        log.info("Request received to endpoint: /users delete with id={}", userId);
        userService.removeUser(userId);
        return HttpStatus.OK;
    }
}
