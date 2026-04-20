package io.github.frizkw.cdekTest.mapper;

import io.github.frizkw.cdekTest.model.Task;
import io.github.frizkw.cdekTest.model.TaskStatus;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface TaskMapper {
    @Insert("INSERT INTO tasks(title, description, status) VALUES(#{title}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);

    @Select("SELECT id, title, description, status FROM tasks WHERE id = #{id}")
    Optional<Task> findById(Long id);

    @Update("UPDATE tasks SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") TaskStatus status);

    @Select("SELECT COUNT(1) > 0 FROM tasks WHERE id = #{id}")
    boolean existsById(Long id);
}
