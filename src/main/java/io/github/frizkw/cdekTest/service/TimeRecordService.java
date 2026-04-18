package io.github.frizkw.cdekTest.service;

import io.github.frizkw.cdekTest.dto.request.CreateTimeRecordRequest;
import io.github.frizkw.cdekTest.dto.response.TimeRecordResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRecordService {
    void saveTimeRecord(CreateTimeRecordRequest request);
    List<TimeRecordResponse> getRecordsByPeriod(Long employeeId, LocalDateTime start, LocalDateTime end);
}