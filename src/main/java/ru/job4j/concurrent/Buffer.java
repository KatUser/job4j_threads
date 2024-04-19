package ru.job4j.concurrent;

public class Buffer {
    private StringBuilder buffer = new StringBuilder();
    /* new Buffer (Объект класса buffer) - monitor */

    public void add(int value) { /* критич секция */
        synchronized (this) { /* monitor */
            System.out.print(value);
            buffer.append(value);
        }
    }

    @Override
    public String toString() { /* критич секция */
        synchronized (this) {
            return buffer.toString();
        }
    }
}