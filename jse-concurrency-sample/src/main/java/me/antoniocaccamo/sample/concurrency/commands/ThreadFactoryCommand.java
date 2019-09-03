package me.antoniocaccamo.sample.concurrency.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.sample.concurrency.factory.CustomThreadFactory;
import me.antoniocaccamo.sample.concurrency.runnables.MyThread;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Command(name = "factory",
        description = "...",
        mixinStandardHelpOptions = true
)
@Slf4j
public class ThreadFactoryCommand implements Runnable {

    @Value("${micronaut.application.name}")
    private String name;

    @Value("${app.file}")
    private String file;

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Inject
    DataSource dataSource;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(ThreadFactoryCommand.class, args);
    }

    public void run() {
        log.info("name {} file {}", name, file);
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2, new CustomThreadFactory());
        try {


            executorService.submit( new MyThread(file) );
            executorService.submit( new MyThread(file) );
            executorService.submit( new MyThread(file) );
            executorService.submit( new MyThread(file) );
            executorService.submit( new MyThread(file) );

            executorService.submit( new MyThread(file) );

        } finally {
            executorService.shutdown();
            try {
                log.info("executorService is shutdown ? {}", executorService.awaitTermination(10, TimeUnit.SECONDS) );
            } catch (InterruptedException e) {
                log.error("error occurred : {}", e.getMessage());
                executorService.shutdownNow();
            }
        }

    }
}
