package me.antoniocaccamo.sample.concurrency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.sample.concurrency.processor.UserProcessor;
import me.antoniocaccamo.sample.concurrency.repo.UserRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "command-thread", description = "...", mixinStandardHelpOptions = true)
@Slf4j
public class CommandExecutor implements Runnable {

    @Value("${micronaut.application.name}")
    private String name;

    @Value("${app.file}")
    private String file;

    @Option(names = { "-v", "--verbose" }, description = "...")
    boolean verbose;

    @Inject
    UserRepository userRepository;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(CommandExecutor.class, args);
    }

    public void run() {
        log.info("name {} file {}", name, file);
        // business logic here

        Callable<List<String>> linesCallable = () -> {
            List<String> lines = new ArrayList<>();
            InputStream is = getClass().getClassLoader().getResourceAsStream(file);
            if (is == null) {
                log.error("{} : not found", file);
                return lines;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    log.info("{} - line : {}", Thread.currentThread().getName(), line);
                    lines.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<String>> linesFuture = executor.submit(linesCallable);

        try {
            List<String> lines = linesFuture.get();
            // log.info("lines : {}" , lines);
            lines.stream().forEach(line -> {
                UserProcessor userProcessor = new UserProcessor(userRepository, line);
                Future<String> future = executor.submit(userProcessor);
                try {
                    future.get();
                } catch (InterruptedException e) {
                    log.error("error occurred : {}", e.getMessage());
                } catch (ExecutionException e) {
                    log.error("error occurred : {}", e.getMessage());
                }
            });
            userRepository.findAll().stream().forEach(x -> log.info("{}", x));
        } catch (InterruptedException e) {
            log.error("error occurred : {}", e.getMessage());
        } catch (ExecutionException e) {
            log.error("error occurred : {}", e.getMessage());
        } catch (Exception e) {
            log.error("error occurred : {}", e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

}
