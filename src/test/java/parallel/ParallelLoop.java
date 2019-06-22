/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package parallel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ParallelLoop {

    private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();

    @Test
    void test() {
        var resultArr = new int[10];

        getSinTable(resultArr, 0, resultArr.length);
        print(resultArr);

        resultArr = new int[10];
        parallelWithOnlyThreads(resultArr);
        print(resultArr);

        resultArr = new int[10];
        parallelWithThreadPool(resultArr);
        print(resultArr);

        resultArr = new int[10];
        parallelWithForkJoinPool(resultArr);

        resultArr = declarativeParallelLoop();
        print(resultArr);
    }

    private void getSinTable(int[] array, int from, int to) {
        for (var i = from; i < to; i++) {
            var sinTheta = Math.sin(i * 10d);
            var sinThetaRounded = Math.round(sinTheta);
            array[i] = Math.toIntExact(sinThetaRounded);
        }
    }

    private void parallelWithOnlyThreads(final int[] array) {
        var threads = new Thread[AVAILABLE_CORES + 1];
        final var segmentLen = array.length / AVAILABLE_CORES;
        var offset = 0;
        for (var i = 0; i <= AVAILABLE_CORES; i++) {
            final var from = offset;
            final var to = offset + segmentLen;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    getSinTable(array, from, to);
                }
            });
            threads[i].start();
            offset += segmentLen;
        }
        getSinTable(array, array.length - segmentLen, array.length);

        for (var i = 0; i <= AVAILABLE_CORES; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignore) {
            }
        }
    }

    private void parallelWithThreadPool(final int[] array) {
        var exec = Executors.newFixedThreadPool(AVAILABLE_CORES);
        var segmentLen = array.length / AVAILABLE_CORES;
        if (segmentLen == 0) {
            segmentLen = array.length;
        }
        var offset = 0;
        var noOfCores = AVAILABLE_CORES;
        while (noOfCores-- >= 0) {
            final var from = offset;
            final var to = offset + segmentLen;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    getSinTable(array, from, to);
                }
            });
            offset += segmentLen;
        }
        getSinTable(array, array.length - segmentLen, array.length);

        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignore) {
        }
    }

    private void parallelWithForkJoinPool(final int[] array) {
        var pool = new ForkJoinPool(AVAILABLE_CORES);
        pool.invoke(new ForEach(array, 0, array.length));
    }

    class ForEach extends RecursiveAction {
        private final int[] array;
        private final int from;
        private final int to;

        // In real world, don't have it below 10,000
        static final int TASK_LEN = 2;

        ForEach(int[] array, int from, int to) {
            this.array = array;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            var len = to - from;
            if (len < TASK_LEN) {
                getSinTable(array, from, to);
            } else {
                var mid = (from + to) / 2;
                new ForEach(array, from, mid).fork();
                new ForEach(array, mid, to).fork();
            }
        }
    }

    private int[] declarativeParallelLoop() {
        return IntStream.iterate(0, i -> i + 1).limit(10)
                .parallel()
                .map(ele -> ele * 10)
                .mapToDouble(Math::sin)
                .mapToLong(Math::round)
                .mapToInt(Math::toIntExact)
                .toArray();
    }

    private void print(int[] array) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(array, 0, 10)));
    }

}