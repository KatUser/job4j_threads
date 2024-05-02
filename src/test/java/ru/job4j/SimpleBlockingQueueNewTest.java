package ru.job4j;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueNewTest {
    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {

        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueueNew<Integer> queue = new SimpleBlockingQueueNew<>(100);

        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            value -> {
                                try {
                                    queue.offer(value);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    Thread.currentThread().interrupt();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    public void whenTwoProducersAndOneConsumer() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueueNew<Integer> queue = new SimpleBlockingQueueNew<>(100);

        Thread producerFirst = new Thread(
                () -> {
                    IntStream.range(0, 6).forEach(value -> {
                        try {
                            queue.offer(value);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
        );
        producerFirst.start();

        Thread producerSecond = new Thread(
                () -> {
                    IntStream.range(6, 11).forEach(value -> {
                        try {
                            queue.offer(value);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
        );
        producerSecond.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producerFirst.join();
        producerSecond.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }
}