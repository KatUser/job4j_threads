package ru.job4j;

public class Count {
    private int value;

    public synchronized void increment() { /* блокирует одновременный доступ к методам */
        value++;
    }

    public synchronized int get() {
        return value;
    }
}