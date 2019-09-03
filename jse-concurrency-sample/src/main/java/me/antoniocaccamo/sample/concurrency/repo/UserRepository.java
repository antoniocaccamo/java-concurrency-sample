package me.antoniocaccamo.sample.concurrency.repo;

import me.antoniocaccamo.sample.concurrency.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(@NotNull Integer id);

    User save(@NotNull User user);

    List<User> findAll();
}
