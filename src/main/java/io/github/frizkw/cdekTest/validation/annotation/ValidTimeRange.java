package io.github.frizkw.cdekTest.validation.annotation;

import io.github.frizkw.cdekTest.validation.validator.TimeRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeRangeValidator.class)
@Documented
public @interface ValidTimeRange {

    String message() default "Время окончания должно быть позже времени начала";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
