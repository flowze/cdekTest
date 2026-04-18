package io.github.frizkw.cdekTest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeRecord {
    Long id;
    Long employeeId;
    Long taskId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String workDescription;
}
