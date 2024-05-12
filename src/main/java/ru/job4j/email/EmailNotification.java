package ru.job4j.email;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class EmailNotification {
    private final ExecutorService pool =  Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        AtomicReference<String> subject = new AtomicReference<>(
                String.format("Notification {%s} to email {%s}",
                        user.username(),
                        user.email())
        );
        AtomicReference<String> body = new AtomicReference<>(
                String.format("Add a new event to {%s}%n",
                        user.username())
        );
        pool.submit(() -> send(subject.get(), body.get(), user.email()));
    }

    public void send(String subject, String body, String email) {
        System.out.println("Sent email : " + subject + " " + body + " " + email);
    }

    public void close() {
        pool.shutdown();
    }
}
