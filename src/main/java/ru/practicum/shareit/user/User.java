package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.validator.EmailRFC2822;

import javax.validation.constraints.NotBlank;

/**
 * Класс описывает модель User
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Email cannot be empty")
    @EmailRFC2822(message = "Email must contain the @ symbol and match the pattern rfc2822")
    private String email;
}
