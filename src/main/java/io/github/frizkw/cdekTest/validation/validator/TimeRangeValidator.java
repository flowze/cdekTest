package io.github.frizkw.cdekTest.validation.validator;

import io.github.frizkw.cdekTest.dto.request.CreateTimeRecordRequest;
import io.github.frizkw.cdekTest.validation.annotation.ValidTimeRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, CreateTimeRecordRequest> {
    @Override
    public boolean isValid(CreateTimeRecordRequest value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        LocalDateTime start = value.startTime();
        LocalDateTime end = value.endTime();

        if (start == null || end == null) {
            return true; // null проверяется @NotNull
        }

        return end.isAfter(start);
    }
}
