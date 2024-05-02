package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int queueSizeLimit;

    public SimpleBlockingQueue(int queueSizeLimit) {
        this.queueSizeLimit = queueSizeLimit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == queueSizeLimit) {
                wait();
            }
        queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }
}
