package me.antoniocaccamo.sample.concurrency.commands;

import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@CommandLine.Command(name = "schedule",
        description = "...",
        mixinStandardHelpOptions = true
)
@Slf4j
public class ScheduleCommand implements Runnable {

    @Value("${app.dir}")
    private String dir;

    @Value("${app.schedule.time-unit}")
    private String timeUnit;

    @Value("${app.schedule.delay}")
    private long delay;

    @Override
    public void run() {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        try {


            Runnable runnable = () -> {
                for (File file : Arrays.asList(new File(dir).listFiles())) {
                    log.info("file [{}] : {}",
                            file.getAbsolutePath(),
                            Duration.ofMillis(System.currentTimeMillis() - file.lastModified()).getSeconds()
                    );
                }

            };
            TimeUnit tu = TimeUnit.valueOf(timeUnit);
            log.info("waiting for {} {} before start", delay, tu);
            scheduledExecutorService.schedule(runnable, 5, tu);
        } finally {
            scheduledExecutorService.shutdown();
            try {
                log.info("scheduledExecutorService is shutdown ? {}", scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS) );
            } catch (InterruptedException e) {
                log.error("error occurred : {}", e.getMessage());
                scheduledExecutorService.shutdownNow();
            }
        }
    }
}
