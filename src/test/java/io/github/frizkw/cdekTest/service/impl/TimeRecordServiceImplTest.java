package io.github.frizkw.cdekTest.service.impl;

import io.github.frizkw.cdekTest.dto.request.CreateTimeRecordRequest;
import io.github.frizkw.cdekTest.dto.response.TimeRecordResponse;
import io.github.frizkw.cdekTest.exception.ResourceNotFoundException;
import io.github.frizkw.cdekTest.mapper.TaskMapper;
import io.github.frizkw.cdekTest.mapper.TimeRecordMapper;
import io.github.frizkw.cdekTest.model.TimeRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceImplTest {

    @Mock
    private TimeRecordMapper timeRecordMapper;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TimeRecordServiceImpl timeRecordService;

    private final Long employeeId = 1L;
    private final Long taskId = 10L;
    private final LocalDateTime start = LocalDateTime.now().minusHours(2);
    private final LocalDateTime end = LocalDateTime.now();

    @Test
    void shouldSaveTimeRecordSuccessfully() {
        CreateTimeRecordRequest request = new CreateTimeRecordRequest(
                employeeId,
                taskId,
                start,
                end,
                "work"
        );
        when(taskMapper.existsById(taskId)).thenReturn(true);


        timeRecordService.saveTimeRecord(request);

        verify(timeRecordMapper).insert(any(TimeRecord.class));
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        CreateTimeRecordRequest request = new CreateTimeRecordRequest(
                employeeId,
                taskId,
                start,
                end,
                "work"
        );

        when(taskMapper.existsById(taskId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> timeRecordService.saveTimeRecord(request));

        verify(timeRecordMapper, never()).insert(any());
    }

    @Test
    void shouldReturnRecordsByPeriod() {
        TimeRecord record = TimeRecord.builder()
                .id(100L)
                .taskId(taskId)
                .startTime(start)
                .endTime(end)
                .workDescription("desc")
                .build();

        when(timeRecordMapper.findByEmployeeIdAndPeriod(employeeId, start, end))
                .thenReturn(List.of(record));

        List<TimeRecordResponse> result =
                timeRecordService.getRecordsByPeriod(employeeId, start, end);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).id());
        assertEquals(taskId, result.get(0).taskId());

        verify(timeRecordMapper).findByEmployeeIdAndPeriod(employeeId, start, end);
    }
}