package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.email.User;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

        @Test
        void whenLinearSearchForExistingStringIsOk() {
            String[] arr = {"Test1", "Test2", "Test3", "Test4", "Test5",
                    "Test6", "Test7", "Test8", "Test9", "Test10"};
            String searched = "Test10";
            assertThat(ParallelSearch.search(arr, searched)).isEqualTo(9);
        }

    @Test
    void whenLinearSearchForExistingIntegerIsOk() {
        Integer[] arr = {1, 22, 45, 66, 78, 0, 0, 9, 22, 8};
        Integer searched = 8;
        assertThat(ParallelSearch.search(arr, searched)).isEqualTo(9);
    }

    @Test
    void whenRecursiveSearchForExistingIntegerIsOk() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 30, 37};
        Integer searched = 30;
        assertThat(ParallelSearch.search(arr, searched)).isEqualTo(19);
    }

    @Test
    void whenRecursiveSearchForNonExistingIntegerIsOk() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 30, 37};
        Integer searched = 370;
        assertThat(ParallelSearch.search(arr, searched)).isEqualTo(-1);
    }

    @Test
    void whenLinearSearchForNonExistingStringIsOk() {
        String[] arr = {"Test1", "Test2"};
        String searched = "Test0";
        assertThat(ParallelSearch.search(arr, searched)).isEqualTo(-1);
    }

    @Test
    void whenLinearSearchForExistingUserIsOk() {
        User[] arr = {new User("User1", "user1@mail.ru"),
                        new User("User2", "user2@mail.ru")};
        User user = new User("User2", "user2@mail.ru");
        assertThat(ParallelSearch.search(arr, user)).isEqualTo(1);
    }

    @Test
    void whenLinearSearchForNonExistingUserIsOk() {
        User[] arr = {new User("User1", "user1@mail.ru"),
                new User("User2", "user2@mail.ru")};
        User user = new User("User3", "user3@mail.ru");
        assertThat(ParallelSearch.search(arr, user)).isEqualTo(-1);
    }

    @Test
    void whenRecursiveSearchForExistingUserIsOk() {
        User[] arr = new User[21];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new User("User" + i, "user" + i + "@mail.ru");
        }
        User user = new User("User20", "user20@mail.ru");
        assertThat(ParallelSearch.search(arr, user)).isEqualTo(20);
    }

    @Test
    void whenRecursiveSearchForNonExistingUserIsOk() {
        User[] arr = new User[21];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new User("User" + i, "user" + i + "@mail.ru");
        }
        User user = new User("User38", "user38@mail.ru");
        assertThat(ParallelSearch.search(arr, user)).isEqualTo(-1);
    }
}
