package ru.practicum.shareit.booking.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE_USE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CheckDateValidator.class)
public @interface StartBeforeEndDateValid {
    String message() default "Start must be earlier than end or not equal to null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}