package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenCalculatingSumsWithSync() {
        int[][] matrix =
                {{1, 2, 3},
                 {4, 5, 6},
                 {7, 8, 9}};
        Sums expected1 = new Sums(6, 12);
        Sums expected2 = new Sums(15, 15);
        Sums expected3 = new Sums(24, 18);
        Sums[] allExpected = {expected1, expected2, expected3};
        assertThat(RolColSum.sum(matrix)).containsExactly(allExpected);
    }

    @Test
    void whenCalculatingSumsWithAsync() throws ExecutionException, InterruptedException {
        int[][] matrix =
                {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        Sums expected1 = new Sums(6, 12);
        Sums expected2 = new Sums(15, 15);
        Sums expected3 = new Sums(24, 18);
        Sums[] allExpected = {expected1, expected2, expected3};
        assertThat(RolColSum.asyncSum(matrix)).containsExactly(allExpected);
    }
}

