package ru.job4j.concurrent;

public class DaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    System.out.println("I am a deamon thread!");
                }
        );
        thread.setDaemon(true);
        thread.start();
        System.out.println(thread.isDaemon());
    }
}
