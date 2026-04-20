package io.github.frizkw.cdekTest.mapper;

import io.github.frizkw.cdekTest.model.TimeRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TimeRecordMapper {
    @Insert("INSERT INTO time_records(employee_id, task_id, start_time, end_time, work_description) " +
            "VALUES(#{employeeId}, #{taskId}, #{startTime}, #{endTime}, #{workDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TimeRecord timeRecord);

    @Select("""
        SELECT *
        FROM time_records
        WHERE employee_id = #{employeeId}
          AND start_time < #{endDate}
          AND end_time > #{startDate}
    """)
    List<TimeRecord> findByEmployeeIdAndPeriod(
            Long employeeId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
