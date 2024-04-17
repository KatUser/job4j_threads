package ru.job4j.ref;

public class ShareNotSafe {
    /*Treads first и second работают с локальными объектами user.
    Изменение локального объекта не влечет изменений в самом кеше.
    Методы add и findById работают с копиями объекта User. */
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("main");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("first");
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("second");
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(cache.findById(1).getName());
    }
}