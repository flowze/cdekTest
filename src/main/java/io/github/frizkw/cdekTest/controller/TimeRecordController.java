package io.github.frizkw.cdekTest.controller;


import io.github.frizkw.cdekTest.dto.request.CreateTimeRecordRequest;
import io.github.frizkw.cdekTest.dto.response.TimeRecordResponse;
import io.github.frizkw.cdekTest.service.TimeRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/time-records")
@Tag(name = "Time Tracking API", description = "Учет рабочего времени")
@RequiredArgsConstructor
public class TimeRecordController {

    private final TimeRecordService timeRecordService;

    @PostMapping
    @Operation(summary = "Создать запись о затраченном времени")
    public ResponseEntity<Void> createRecord(@Valid @RequestBody CreateTimeRecordRequest request) {
        timeRecordService.saveTimeRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Получить записи времени сотрудника за период")
    public ResponseEntity<List<TimeRecordResponse>> getEmployeeRecords(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<TimeRecordResponse> records = timeRecordService.getRecordsByPeriod(employeeId, start, end);
        return ResponseEntity.ok(records);
    }
}
