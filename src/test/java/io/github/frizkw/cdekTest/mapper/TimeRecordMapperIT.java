package io.github.frizkw.cdekTest.mapper;

import io.github.frizkw.cdekTest.model.Task;
import io.github.frizkw.cdekTest.model.TaskStatus;
import io.github.frizkw.cdekTest.model.TimeRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class TimeRecordMapperIT {

    @Autowired
    private TimeRecordMapper timeRecordMapper;
    @Autowired
    private TaskMapper taskMapper;


    private TimeRecord timeRecord;
    private Task task;

    @BeforeEach
    void setUp() {

        task = new Task();
        task.setTitle("test task");
        task.setDescription("desc");
        task.setStatus(TaskStatus.NEW);

        taskMapper.insert(task);

        timeRecord = new TimeRecord();
        timeRecord.setEmployeeId(1L);
        timeRecord.setTaskId(task.getId());
        timeRecord.setStartTime(LocalDateTime.now().minusHours(2));
        timeRecord.setEndTime(LocalDateTime.now());
        timeRecord.setWorkDescription("integration test");
    }

    @Test
    void shouldInsertAndFindByPeriod() {
        timeRecordMapper.insert(timeRecord);

        List<TimeRecord> result = timeRecordMapper.findByEmployeeIdAndPeriod(
                1L,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        );

        assertThat(result)
                .isNotEmpty()
                .extracting(TimeRecord::getEmployeeId)
                .containsOnly(1L);
    }
}