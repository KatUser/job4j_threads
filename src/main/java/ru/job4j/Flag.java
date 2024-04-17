package ru.job4j;

public class Flag {
    private static volatile boolean flag = true;

    /* при обращении к полю flag процессор
    оборачивает переменную в критическую секцию, обеспечивая тем самым синхронизацию ресурсов.  */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        flag = false;
        thread.join();
    }
}