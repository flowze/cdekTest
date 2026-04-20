package io.github.frizkw.cdekTest.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import io.github.frizkw.cdekTest.dto.request.CreateTaskRequest;
import io.github.frizkw.cdekTest.dto.response.TaskResponse;
import io.github.frizkw.cdekTest.exception.ResourceNotFoundException;
import io.github.frizkw.cdekTest.mapper.TaskMapper;
import io.github.frizkw.cdekTest.model.Task;
import io.github.frizkw.cdekTest.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void shouldCreateTaskSuccessfully() {
        CreateTaskRequest request = new CreateTaskRequest(
                "title",
                "description"
        );

        doAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            return null;
        }).when(taskMapper).insert(any(Task.class));

        TaskResponse response = taskService.createTask(request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskMapper).insert(captor.capture());

        Task saved = captor.getValue();

        assertEquals("title", saved.getTitle());
        assertEquals("description", saved.getDescription());
        assertEquals(TaskStatus.NEW, saved.getStatus());

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void shouldReturnTaskById() {
        Task task = Task.builder()
                .id(1L)
                .title("title")
                .description("desc")
                .status(TaskStatus.NEW)
                .build();

        when(taskMapper.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L);

        assertEquals(1L, response.id());
        assertEquals("title", response.title());
        assertEquals(TaskStatus.NEW, response.status());

        verify(taskMapper).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskMapper.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getTaskById(1L));
    }

    @Test
    void shouldUpdateTaskStatus() {
        when(taskMapper.updateStatus(1L, TaskStatus.IN_PROGRESS)).thenReturn(1);

        taskService.updateStatus(1L, TaskStatus.IN_PROGRESS);

        verify(taskMapper).updateStatus(1L, TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingMissingTask() {
        when(taskMapper.updateStatus(1L, TaskStatus.DONE)).thenReturn(0);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.updateStatus(1L, TaskStatus.DONE));

        verify(taskMapper).updateStatus(1L, TaskStatus.DONE);
    }
}