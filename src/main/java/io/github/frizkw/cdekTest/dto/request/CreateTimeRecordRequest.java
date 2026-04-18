package io.github.frizkw.cdekTest.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTimeRecordRequest(
        @NotNull(message = "ID сотрудника обязателен")
        Long employeeId,
        @NotNull(message = "ID задачи обязателен")
        Long taskId,
        @NotNull(message = "Время начала обязательно")
        LocalDateTime startTime,
        @NotNull(message = "Время окончания обязательно")
        LocalDateTime endTime,
        String workDescription
) {}