package io.github.frizkw.cdekTest.service.impl;

import io.github.frizkw.cdekTest.dto.request.CreateTaskRequest;
import io.github.frizkw.cdekTest.dto.response.TaskResponse;
import io.github.frizkw.cdekTest.exception.ResourceNotFoundException;
import io.github.frizkw.cdekTest.mapper.TaskMapper;
import io.github.frizkw.cdekTest.model.Task;
import io.github.frizkw.cdekTest.model.TaskStatus;
import io.github.frizkw.cdekTest.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskStatus.NEW)
                .build();

        taskMapper.insert(task);
        log.info("Задача создана успешно: id={}, title={}", task.getId(), task.getTitle());
        return mapToResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        return taskMapper.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Задача с ID " + id + " не найдена"));
    }

    @Override
    @Transactional
    public void updateStatus(Long id, TaskStatus status) {
        int updatedRows = taskMapper.updateStatus(id, status);

        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Задача с ID " + id + " не найдена");
        }
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
