package io.github.frizkw.cdekTest.mapper;

import io.github.frizkw.cdekTest.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT id, username, password, role FROM users WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Insert("INSERT INTO users(username, password, role) VALUES(#{username}, #{password}, #{role})")
    void insert(User user);
}
