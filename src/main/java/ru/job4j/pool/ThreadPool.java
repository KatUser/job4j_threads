package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        while (size > 0) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
            );
            size--;
            threads.add(thread);
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i <= 5; i++) {
            int jobNumber = i;
            threadPool.work(() -> {
                System.out.println(Thread.currentThread().getName()
                        + ": Job " + jobNumber);
            });
        }
        threadPool.shutdown();
    }
}