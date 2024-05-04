package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int newValue = count.incrementAndGet();
        int expectedValue;
        do {
            expectedValue = newValue;
        } while (!count.compareAndSet(expectedValue, newValue));
    }

    public int get() {
        return count.get();
    }
}