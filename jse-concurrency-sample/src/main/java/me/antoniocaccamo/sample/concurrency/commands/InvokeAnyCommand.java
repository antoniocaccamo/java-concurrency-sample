package me.antoniocaccamo.sample.concurrency.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Command(name = "invoke-any",
        description = "...",
        mixinStandardHelpOptions = true
)
@Slf4j
public class InvokeAnyCommand implements Runnable {
    
    public static void main(String[] args) throws Exception {
        log.info("at least one task reached the end..");
        PicocliRunner.run(InvokeAnyCommand.class, args);
    }

    public void run() {
        List<Callable<Boolean>> callables = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int i = 0;
        try {
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});

            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});

            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});

            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            log.info("executorService.invokeAny(callables) : {}", executorService.invokeAny(callables) );

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.error("error occurred : {}", e.getMessage());
        }finally {
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