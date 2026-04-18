package io.github.frizkw.cdekTest.dto.response;

import java.time.LocalDateTime;

public record TimeRecordResponse(
        Long id,
        Long taskId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String workDescription
) {}
