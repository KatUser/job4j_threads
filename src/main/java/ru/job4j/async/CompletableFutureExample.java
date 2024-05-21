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
                    System.out.println("Кошка : я купип жрадло " + catFood);
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

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын : я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын : руки я помыл");
        });
        iWork();
    }

    public static  void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Хлеп")
                .thenApply((product) -> "Кошка : держи " + product);
        iWork();
        System.out.println(bm.get());
    }

    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> ct = buyCatFood("Вискас");
        ct.thenAccept((catFood) -> System.out.println("Кошка : поставила "
                + catFood + " на полку"));
        iWork();
        System.out.println("Куплено " + ct.get());
    }

    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = feedCat().thenCompose(
                p -> buyCatFood("Вискассс")
        );
        result.get();
    }

    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyCatFood("Риба")
                .thenCombine(buyCatFood("Сухой корм"),
                        (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static CompletableFuture<Void> washPaws(String name) {
        return CompletableFuture.runAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + " моет лапки");
                }
        );
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washPaws("Буся"), washPaws("Пушок"),
                washPaws("Вася"), washPaws("Барсик")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoIsWashingPaws(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return name + " моет лапки";
                }
        );
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoIsWashingPaws("Буся"), whoIsWashingPaws("Пушок"),
                whoIsWashingPaws("Вася"), whoIsWashingPaws("Барсик")
        );
        System.out.println("Кто сейчас моет лапки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        runAsyncExample();
        supplyAsyncExample();
        thenRunExample();
        thenAcceptExample();
        thenComposeExample();
        thenCombineExample();
        allOfExample();
        anyOfExample();
        thenApplyExample();
    }
}
