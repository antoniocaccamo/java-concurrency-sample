package me.antoniocaccamo.sample.concurrency.factory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CustomThreadFactory implements ThreadFactory {

    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName( String.format("name %d", counter.incrementAndGet()));
        return t;
    }
}
