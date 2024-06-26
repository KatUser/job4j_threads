package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T searched;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T searched, int from, int to) {
        this.array = array;
        this.searched = searched;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= 10) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> parallelSearchLeft = new ParallelSearch<>(
                array, searched, from, middle);
        ParallelSearch<T> parallelSearchRight = new ParallelSearch<>(
                array, searched, middle + 1, to);
        parallelSearchLeft.fork();
        parallelSearchRight.fork();
        int left = parallelSearchLeft.join();
        int right = parallelSearchRight.join();

        return Math.max(left, right);
    }

    private Integer linearSearch() {
        int res = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(searched)) {
                res = i;
                break;
            }
        }
        return res;
    }

    public static <T> int search(T[] array, T searched) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(
                new ParallelSearch<>(array, searched, 0, array.length - 1));
    }
}
