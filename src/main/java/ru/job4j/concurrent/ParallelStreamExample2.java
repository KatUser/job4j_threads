package ru.job4j.concurrent;

import java.util.stream.IntStream;

public class ParallelStreamExample2 {
    public static void main(String[] args) {
        IntStream parallel = IntStream.range(1, 2).parallel();
        System.out.println(parallel.isParallel());
        IntStream seq = parallel.sequential();
        System.out.println(seq.isParallel());
    }
}
