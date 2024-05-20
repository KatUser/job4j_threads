package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int count = matrix.length;
        Sums[] array = new Sums[count];
        for (int i = 0; i < count; i++) {
            array[i] = countSums(matrix, i);
        }
        return array;
    }

    /**
     * Async
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int count = matrix.length;
        Sums[] array = new Sums[count];
        List<CompletableFuture<Sums>> tasks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int finalI = i;
            tasks.add(CompletableFuture.supplyAsync(() -> countSums(matrix, finalI)));
        }
        for (int i = 0; i < count; i++) {
            array[i] = tasks.get(i).get();
        }
        return array;
    }

    private static Sums countSums(int[][] matrix, int i) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[i][j];
            colSum += matrix[j][i];
        }
        return new Sums(rowSum, colSum);
    }
}