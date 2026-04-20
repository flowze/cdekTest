package io.github.frizkw.cdekTest.dto.response;

import java.time.LocalDateTime;

public record ErrorDetails(
        LocalDateTime timestamp,
        String message,
        String details,
        int status
) {}