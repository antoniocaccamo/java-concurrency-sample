package me.antoniocaccamo.sample.concurrency.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Command(name = "invoke-all",
        description = "...",
        mixinStandardHelpOptions = true
)
@Slf4j
public class InvokeAllCommand  implements Runnable {
    
    public static void main(String[] args) throws Exception {
        log.info("all task reached the end..");
        PicocliRunner.run(InvokeAllCommand.class, args);
    }

    public void run() {
        List<Callable<Boolean>> callables = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            callables.add( () -> {log.info("just logging..."); return true;});
            List<Future<Boolean>> futures = executorService.invokeAll(callables);
            for (Future<Boolean> fb : futures  ) {
                log.info("fb.get() : {}", fb.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.error("error occurred : {}", e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }

}