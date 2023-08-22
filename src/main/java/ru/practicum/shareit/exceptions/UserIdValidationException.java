package ru.practicum.shareit.exceptions;

public class UserIdValidationException extends RuntimeException {
    public UserIdValidationException() {
        super();
    }

    public UserIdValidationException(String message) {
        super(message);
    }

    public UserIdValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdValidationException(Throwable cause) {
        super(cause);
    }
}
