package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenTwoThreads() throws InterruptedException {
        CASCount casCount = new CASCount();

        Thread threadFirst = new Thread(
                casCount::increment
        );

        Thread threadSecond = new Thread(
                casCount::increment
        );

        threadSecond.start();
        threadFirst.start();
        threadSecond.join();
        threadFirst.join();

        assertThat(casCount.get()).isEqualTo(2);
    }

    @Test
    public void whenTwoThreadsAndOneWithForCycle() throws InterruptedException {
        CASCount casCount = new CASCount();

        Thread threadFirst = new Thread(
                casCount::increment
        );

        Thread threadSecond = new Thread(
                () -> {
                    for (int i = casCount.get(); i <= 100; i++) {
                        casCount.increment();
                    }
                }
        );

        threadFirst.start();
        threadFirst.join();

        threadSecond.start();
        threadSecond.join();

        assertThat(casCount.get()).isEqualTo(101);
    }

    @Test
    public void whenThreeThreads() throws InterruptedException {
        CASCount casCount = new CASCount();

        Thread threadFirst = new Thread(
                casCount::increment
        );

        Thread threadSecond = new Thread(
                casCount::increment
        );

        Thread threadThird = new Thread(
                casCount::increment
        );

        threadThird.start();
        threadSecond.start();
        threadFirst.start();

        threadThird.join();
        threadSecond.join();
        threadFirst.join();

        assertThat(casCount.get()).isEqualTo(3);
    }
}