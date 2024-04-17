package ru.job4j;

public final class DCLSingleton {

    private static volatile DCLSingleton instance; /*volatile, то чтение и запись переменной будет происходить только из RAM памяти процессора.*/

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) { /*  Монитор будет сам класс DCLSingleton */
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    private DCLSingleton() {
    }
}