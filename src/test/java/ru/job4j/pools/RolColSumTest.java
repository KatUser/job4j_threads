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
        RolColSum.Sums expected1 = new RolColSum.Sums(6, 12);
        RolColSum.Sums expected2 = new RolColSum.Sums(15, 15);
        RolColSum.Sums expected3 = new RolColSum.Sums(24, 18);
        RolColSum.Sums[] allExpected = {expected1, expected2, expected3};
        assertThat(RolColSum.sum(matrix)).containsExactly(allExpected);
    }

    @Test
    void whenCalculatingSumsWithAsync() throws ExecutionException, InterruptedException {
        int[][] matrix =
                {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}};
        RolColSum.Sums expected1 = new RolColSum.Sums(6, 12);
        RolColSum.Sums expected2 = new RolColSum.Sums(15, 15);
        RolColSum.Sums expected3 = new RolColSum.Sums(24, 18);
        RolColSum.Sums[] allExpected = {expected1, expected2, expected3};
        assertThat(RolColSum.asyncSum(matrix)).containsExactly(allExpected);
    }
}

