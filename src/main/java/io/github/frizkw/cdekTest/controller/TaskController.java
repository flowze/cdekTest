package io.github.frizkw.cdekTest.controller;


import io.github.frizkw.cdekTest.dto.request.CreateTaskRequest;
import io.github.frizkw.cdekTest.dto.response.TaskResponse;
import io.github.frizkw.cdekTest.model.TaskStatus;
import io.github.frizkw.cdekTest.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task API", description = "Управление задачами")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Создать новую задачу")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.createTask(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о задаче")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Изменить статус задачи")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        taskService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
