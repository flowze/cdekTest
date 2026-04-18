package io.github.frizkw.cdekTest.service;

import io.github.frizkw.cdekTest.dto.request.CreateTaskRequest;
import io.github.frizkw.cdekTest.dto.response.TaskResponse;
import io.github.frizkw.cdekTest.model.TaskStatus;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest request);
    TaskResponse getTaskById(Long id);
    void updateStatus(Long id, TaskStatus status);
}
