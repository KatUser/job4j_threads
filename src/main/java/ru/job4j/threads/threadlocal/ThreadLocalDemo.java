package ru.job4j.threads.threadlocal;

public class ThreadLocalDemo {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException, IllegalThreadStateException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();

        threadLocal.set("This is MAIN thread.");
        System.out.println(threadLocal.get());
        first.start();
        second.start();
        first.start();
        second.start();
    }
}
