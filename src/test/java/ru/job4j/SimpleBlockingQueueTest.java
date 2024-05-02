package ru.job4j;

import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest {

    @Test
    void whenProducerStartsFirst() throws InterruptedException {

        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(2);

        Thread producerThread = new Thread(
                () -> simpleBlockingQueue.offer(1)
        );

        Thread consumerThread = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        producerThread.start();
        producerThread.join();

        consumerThread.start();
        consumerThread.join();
    }

    @Test
    void whenConsumerStartsFirst() throws InterruptedException {

        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(2);

        Thread producerThread = new Thread(
                () -> simpleBlockingQueue.offer(1)
        );

        Thread consumerThread = new Thread(
                () -> {
                    try {
                        simpleBlockingQueue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        consumerThread.start();
        producerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}