package me.antoniocaccamo.sample.concurrency.myibatis.mapper;

import me.antoniocaccamo.sample.concurrency.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from people where id=#{id}")
    User findById(Integer id);

    @Insert({"insert into people(name, email) values(#{user.name}, #{user.email})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(@Param("user") User user);

    @Select("select * from people ")
    List<User> findAll();
}
