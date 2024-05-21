package ru.job4j.pools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    private int[][] matrix;

    private Sums[] allExpected;

    @BeforeEach
    public void init() {
        matrix =
                new int[][]{{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}};
        allExpected =
                new Sums[]{new Sums(6, 12),
                        new Sums(15, 15),
                        new Sums(24, 18)};
    }

    @Test
    void whenCalculatingSumsWithSync() {
        assertThat(RolColSum.sum(matrix)).containsExactly(allExpected);
    }

    @Test
    void whenCalculatingSumsWithAsync() throws ExecutionException, InterruptedException {
        assertThat(RolColSum.asyncSum(matrix)).containsExactly(allExpected);
    }
}

