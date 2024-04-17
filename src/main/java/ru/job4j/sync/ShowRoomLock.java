package ru.job4j.sync;

public class ShowRoomLock {
    /* Монитор - это объект ShowRoomLock */
    public Object lockOfInstance() {
        synchronized (this) {
            return null;
        }
    }

    /* Монитор будет сам класс ShowRoomLock */
    public static Object lockOfClass() {
        synchronized (ShowRoomLock.class) {
            return null;
        }
    }
}