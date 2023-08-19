package ru.practicum.shareit.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailRFC2822ConstraintValidator implements ConstraintValidator<EmailRFC2822, String> {

    private static final Pattern rfc2822 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    @Override
    public void initialize(EmailRFC2822 constraintAnnotation) {
        //Это оставим пустым, так как нет необходимости инициализировать аннотацию.
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }

        return rfc2822.matcher(email).matches();
    }
}