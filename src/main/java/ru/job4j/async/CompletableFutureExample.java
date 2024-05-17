package ru.job4j.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    public static CompletableFuture<String> buyCatFood(String catFood) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Кошка : я пошла за вискасом");
                    try {
                        TimeUnit.SECONDS.sleep(7);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Кошка : я купип жрадло");
                    return catFood;
                }
        );
    }

    public static CompletableFuture<Void> feedCat() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Кошка : я поим сама");
                    try {
                        TimeUnit.SECONDS.sleep(7);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Кошка : я покушап");
                }
        );
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> feedCat = feedCat();
        iWork();
    }

    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> ct = buyCatFood("рибка");
        iWork();
        System.out.println("Куплено: " + ct.get());
    }

    public static void main(String[] args) throws Exception {
        runAsyncExample();
        supplyAsyncExample();
    }
}
