package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {

        CountBarrier countBarrier = new CountBarrier(1);

        Thread threadTwo = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread()
                            .getName() + " started;");
                }
        );

        Thread threadOne = new Thread(
                () -> {
                    countBarrier.count();
                    System.out.println(Thread.currentThread()
                            .getName() + " started;");
                }
        );
        threadOne.start();
        threadTwo.start();
    }
}