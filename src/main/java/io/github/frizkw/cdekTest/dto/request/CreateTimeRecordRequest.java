package io.github.frizkw.cdekTest.dto.request;

import io.github.frizkw.cdekTest.validation.annotation.ValidTimeRange;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@ValidTimeRange
public record CreateTimeRecordRequest(
        @NotNull(message = "ID сотрудника обязателен")
        Long employeeId,
        @NotNull(message = "ID задачи обязателен")
        Long taskId,
        @NotNull(message = "Время начала обязательно")
        LocalDateTime startTime,
        @NotNull(message = "Время окончания обязательно")
        LocalDateTime endTime,
        @Size(max = 1000, message = "Описание слишком длинное")
        String workDescription
) {}