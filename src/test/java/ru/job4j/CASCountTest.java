package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenTwoThreads() throws InterruptedException {
        CASCount casCount = new CASCount();

        Thread threadOne = new Thread(
                casCount::increment
        );

        Thread threadTwo = new Thread(
                casCount::increment
        );

        threadTwo.start();
        threadOne.start();
        threadTwo.join();
        threadOne.join();

        assertThat(casCount.get()).isEqualTo(2);
    }

    @Test
    public void whenTwoThreadsAndOneWithForCycle() throws InterruptedException {
        CASCount casCount = new CASCount();

        Thread threadOne = new Thread(
                casCount::increment
        );

        Thread threadTwo = new Thread(
                () -> {
                    for (int i = casCount.get(); i <= 100; i++) {
                        casCount.increment();
                    }
                }
        );

        threadOne.start();
        threadOne.join();

        threadTwo.start();
        threadTwo.join();

        assertThat(casCount.get()).isEqualTo(101);
    }
}