package ru.job4j.threads.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadId {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private static final ThreadLocal<Integer> THREAD_ID =
            ThreadLocal.withInitial(NEXT_ID::getAndIncrement);

    public static int get() {
        return THREAD_ID.get();
    }

    public static void main(String[] args) {

    }
}
