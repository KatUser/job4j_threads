package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public String toString() {
            return String.format("{sum of rows: %s} {sum of columns: %s}", rowSum, colSum);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

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