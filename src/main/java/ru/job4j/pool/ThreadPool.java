package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            threads.add(
                    new Thread(() -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                    })
            );
            threads.get(i).start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 5; i++) {
            threadPool.work(() -> System.out.println("Job is in running with "
            + Thread.currentThread().getName()));
        }
        threadPool.shutdown();
    }
}