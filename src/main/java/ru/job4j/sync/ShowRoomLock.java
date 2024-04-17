package ru.job4j.sync;

public class ShowRoomLock {
    /* Монитор - это объект ShowRoomLock */
    public void lockOfInstance() {
        synchronized (this) {
        }
    }

    /* Монитор будет сам класс ShowRoomLock */
    public static void lockOfClass() {
        synchronized (ShowRoomLock.class) {
        }
    }
}