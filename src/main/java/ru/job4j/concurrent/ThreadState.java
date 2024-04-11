package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread firstThread = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread secondThread = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.printf("%s %s%n", firstThread.getName(), firstThread.getState());
        System.out.printf("%s %s%n", secondThread.getName(), secondThread.getState());
        firstThread.start();
        secondThread.start();
        while (firstThread.getState() != Thread.State.TERMINATED || secondThread.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s %s%n", firstThread.getName(), firstThread.getState());
            System.out.printf("%s %s%n", secondThread.getName(), secondThread.getState());
        }
        System.out.printf("%s %s%n", firstThread.getName(), firstThread.getState());
        System.out.printf("%s %s%n", secondThread.getName(), secondThread.getState());
        System.out.println("ALL THREADS FINISHED");
    }
}