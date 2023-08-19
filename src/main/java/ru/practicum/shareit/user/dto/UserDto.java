package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.validator.EmailRFC2822;

import javax.validation.constraints.NotBlank;
@Data
@Builder
public class UserDto {
        private Long id;
        @NotBlank(message = "Name cannot be empty")
        private String name;
        @NotBlank(message = "Email cannot be empty")
        @EmailRFC2822(message = "Email must contain the @ symbol and match the pattern rfc2822")
        private String email;
}
