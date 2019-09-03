package me.antoniocaccamo.sample.concurrency.repo.impl;

import io.micronaut.validation.Validated;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.sample.concurrency.model.User;
import me.antoniocaccamo.sample.concurrency.myibatis.mapper.UserMapper;
import me.antoniocaccamo.sample.concurrency.repo.UserRepository;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton @Validated @Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(@NotNull Integer id) {
        return Optional.ofNullable(userMapper.findById(id));
    }

    @Override
    public User save(@NotNull User user) {
        userMapper.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userMapper.findAll();
        log.info("users : {}" , users);
        return users;
    }
}
