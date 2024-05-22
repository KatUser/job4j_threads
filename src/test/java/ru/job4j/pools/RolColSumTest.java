package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenCalculatingSumsWithSync() {

        int[][] matrix = new int[][]{{1, 2, 3},
                                     {4, 5, 6},
                                     {7, 8, 9}};

        Sums[] allExpected = new Sums[]{new Sums(6, 12),
                        new Sums(15, 15),
                        new Sums(24, 18)};

        assertThat(RolColSum.sum(matrix)).containsExactly(allExpected);
    }

    @Test
    void whenCalculatingSumsWithAsync() throws ExecutionException, InterruptedException {

        int[][] matrix = new int[][]{{1, 2, 3},
                                     {4, 5, 6},
                                     {7, 8, 9}};

        Sums[] allExpected = new Sums[]{new Sums(6, 12),
                        new Sums(15, 15),
                        new Sums(24, 18)};

        assertThat(RolColSum.asyncSum(matrix)).containsExactly(allExpected);
    }
}

