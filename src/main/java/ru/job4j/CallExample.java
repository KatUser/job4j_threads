package ru.job4j;

import java.util.concurrent.Callable;

public class CallExample {
    Callable<String> task = new Callable<>() {
        @Override
        public String call() {
            return "This is a callable type task!";
        }
    };

    /*
    Callable<String> task = () -> "Это задача типа Callable!";
     */
}

