package io.github.frizkw.cdekTest.service.impl;

import io.github.frizkw.cdekTest.dto.request.CreateTimeRecordRequest;
import io.github.frizkw.cdekTest.dto.response.TimeRecordResponse;
import io.github.frizkw.cdekTest.exception.ResourceNotFoundException;
import io.github.frizkw.cdekTest.mapper.TaskMapper;
import io.github.frizkw.cdekTest.mapper.TimeRecordMapper;
import io.github.frizkw.cdekTest.model.TimeRecord;
import io.github.frizkw.cdekTest.service.TimeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeRecordServiceImpl implements TimeRecordService {
    private final TimeRecordMapper timeRecordMapper;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public void saveTimeRecord(CreateTimeRecordRequest request) {
        // 1. Проверяем, существует ли задача, к которой привязывается время
        if (!taskMapper.existsById(request.taskId())) {
            throw new ResourceNotFoundException("Задача с ID " + request.taskId() + " не найдена");
        }

        // 2. Валидация временного отрезка
        if (request.endTime().isBefore(request.startTime())) {
            throw new IllegalArgumentException("Время окончания не может быть раньше времени начала");
        }

        // 3. Создаем сущность через Builder
        TimeRecord entity = TimeRecord.builder()
                .employeeId(request.employeeId())
                .taskId(request.taskId())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .workDescription(request.workDescription())
                .build();

        // 4. Сохраняем в базу
        timeRecordMapper.insert(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeRecordResponse> getRecordsByPeriod(Long employeeId, LocalDateTime start, LocalDateTime end) {
        // Получаем список из MyBatis
        List<TimeRecord> records = timeRecordMapper.findByEmployeeIdAndPeriod(employeeId, start, end);

        // Преобразуем список сущностей в список DTO
        return records.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TimeRecordResponse mapToResponse(TimeRecord entity) {
        return new TimeRecordResponse(
                entity.getId(),
                entity.getTaskId(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getWorkDescription()
        );
    }
}
