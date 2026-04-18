package io.github.frizkw.cdekTest.service.impl;

import io.github.frizkw.cdekTest.dto.request.CreateTaskRequest;
import io.github.frizkw.cdekTest.dto.response.TaskResponse;
import io.github.frizkw.cdekTest.exception.ResourceNotFoundException;
import io.github.frizkw.cdekTest.mapper.TaskMapper;
import io.github.frizkw.cdekTest.model.Task;
import io.github.frizkw.cdekTest.model.TaskStatus;
import io.github.frizkw.cdekTest.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    @Override
    @Transactional // Перекрываем для методов, изменяющих данные
    public TaskResponse createTask(CreateTaskRequest request) {
        // 1. Создаем Entity из DTO
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskStatus.NEW) // Начальный статус по умолчанию
                .build();

        // 2. Сохраняем в БД через MyBatis
        // После выполнения insert, MyBatis запишет сгенерированный ID в объект task
        taskMapper.insert(task);

        // 3. Возвращаем Response DTO
        return mapToResponse(task);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskMapper.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Задача с ID " + id + " не найдена"));
    }

    @Override
    @Transactional
    public void updateStatus(Long id, TaskStatus status) {
        // Сначала проверяем, существует ли задача
        if (!taskMapper.existsById(id)) {
            throw new ResourceNotFoundException("Невозможно обновить статус: задача с ID " + id + " не найдена");
        }

        // Обновляем статус
        taskMapper.updateStatus(id, status);
    }

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
