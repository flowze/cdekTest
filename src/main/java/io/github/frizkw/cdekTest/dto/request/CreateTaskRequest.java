package io.github.frizkw.cdekTest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank(message = "Название задачи не может быть пустым")
        @Size(max = 255)
        String title,
        String description
) {}
