package io.github.frizkw.cdekTest.dto.response;

import io.github.frizkw.cdekTest.model.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status
) {}
