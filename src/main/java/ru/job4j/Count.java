package ru.job4j;

public class Count {
    private int value;

    public void increment() { /* блокирует одновременный доступ к методам */
        synchronized (this) {
            value++;
        }
    }

    public int get() {
        synchronized (this) {
            return value;
        }
    }
}