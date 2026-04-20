package io.github.frizkw.cdekTest.mapper;

import io.github.frizkw.cdekTest.model.Task;
import io.github.frizkw.cdekTest.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class TaskMapperIT {

    @Autowired
    private TaskMapper taskMapper;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("integration task");
        task.setDescription("test description");
        task.setStatus(TaskStatus.NEW);
    }

    @Test
    void shouldInsertAndFindById() {
        taskMapper.insert(task);

        Optional<Task> result = taskMapper.findById(task.getId());

        assertThat(result)
                .isPresent()
                .get()
                .satisfies(t -> {
                    assertThat(t.getTitle()).isEqualTo("integration task");
                    assertThat(t.getStatus()).isEqualTo(TaskStatus.NEW);
                });
    }

    @Test
    void shouldUpdateStatus() {
        taskMapper.insert(task);

        int updatedRows = taskMapper.updateStatus(task.getId(), TaskStatus.DONE);

        assertThat(updatedRows).isEqualTo(1);

        Optional<Task> updated = taskMapper.findById(task.getId());

        assertThat(updated)
                .isPresent()
                .get()
                .extracting(Task::getStatus)
                .isEqualTo(TaskStatus.DONE);
    }

    @Test
    void shouldReturnZeroWhenUpdatingNonExistingTask() {
        int updatedRows = taskMapper.updateStatus(999L, TaskStatus.DONE);

        assertThat(updatedRows).isEqualTo(0);
    }
    @Test
    void shouldCheckExistsById() {
        taskMapper.insert(task);

        boolean exists = taskMapper.existsById(task.getId());

        assertThat(exists).isTrue();
    }
}