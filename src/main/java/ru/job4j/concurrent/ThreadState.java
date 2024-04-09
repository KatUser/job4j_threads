package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {

        Thread firstThread = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        Thread secondThread = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        firstThread.start();
        secondThread.start();

        while (true) {
            if (firstThread.getState() == Thread.State.TERMINATED
                    && secondThread.getState() == Thread.State.TERMINATED) {
                System.out.println(firstThread.getState());
                System.out.println(secondThread.getState());
                System.out.println("The end");
                break;
            }
        }
    }
}
