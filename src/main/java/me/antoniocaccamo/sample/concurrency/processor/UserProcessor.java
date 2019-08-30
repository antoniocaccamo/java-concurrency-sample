package me.antoniocaccamo.sample.concurrency.processor;

import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.sample.concurrency.model.User;
import me.antoniocaccamo.sample.concurrency.repo.UserRepository;

@Prototype @Slf4j
public class UserProcessor implements Callable<String> {

    private final UserRepository userRepository;
    private  final String line;

    private  final ObjectMapper mapper = new ObjectMapper();

    public UserProcessor(UserRepository userRepository, String line) {
        this.userRepository = userRepository;
        this.line = line;
    }

    @Override
    public String call()   {

        try {
            User user = mapper.readValue(line, User.class);
            log.info("user : {}", user);
            return mapper.writeValueAsString( userRepository.save(user) );
        } catch (Exception e) {
            log.error("error occurred : {}", e.getMessage());
        }
        return null;
    }
}
