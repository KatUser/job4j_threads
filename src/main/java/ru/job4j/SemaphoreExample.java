package ru.job4j;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Runnable task = () -> {
            try {
                semaphore.acquire(); /* запрашивается разрешение
                на выполнение своей работы (или получения доступа к объекту) */
                System.out.println("Thread has made its job!");
                /* После выполнения нитью своей работы,
                разрешение возвращается обратно в семафор:*/
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        Thread.sleep(3000);
        semaphore.release(1);
    }
}
