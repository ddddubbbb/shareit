package ru.practicum.shareit.booking.validate;

import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

public class CheckDateValidator implements ConstraintValidator<StartBeforeEndDateValid, BookItemRequestDto> {

    @Override
    public void initialize(StartBeforeEndDateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookItemRequestDto bookingDto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = bookingDto.getStart();
        LocalDateTime end = bookingDto.getEnd();
        if (start == null || end == null) {
            return false;
        }
        return start.isBefore(end);
    }
}