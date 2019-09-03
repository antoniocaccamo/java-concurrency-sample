package me.antoniocaccamo.sample.concurrency;


import io.micronaut.configuration.picocli.PicocliRunner;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.sample.concurrency.commands.*;
import picocli.CommandLine;

@CommandLine.Command(
        name = "main",
        description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {
                ThreadCommand.class,
                ExecutorCommand.class,
                InvokeAllCommand.class,
                InvokeAnyCommand.class,
                ShutdownCommand.class,
                ScheduleCommand.class,
                ThreadFactoryCommand.class
        }

)
@Slf4j
public class Main implements Runnable {

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Main.class, args);
    }

    @Override
    public void run() {

    }


}
