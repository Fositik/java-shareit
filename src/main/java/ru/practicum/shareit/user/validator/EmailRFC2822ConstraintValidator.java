package ru.practicum.shareit.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static ru.practicum.shareit.constants.HeaderConstants.rfc2822;

public class EmailRFC2822ConstraintValidator implements ConstraintValidator<EmailRFC2822, String> {


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