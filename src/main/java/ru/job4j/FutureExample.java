package ru.job4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureExample {
    public static void main(String[] args) throws Exception {
        Callable<String> task = () -> "Future Task!";
        FutureTask<String> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
