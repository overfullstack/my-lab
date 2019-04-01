/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package concurrency;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ParallelLoop {

    private static final int N_THREADS = Runtime.getRuntime().availableProcessors();

    private void getSinTable(int[] array, int from, int to) {
        for (int j = from; j < to; j++) {
            double sinTheta = Math.sin(j * 10d);
            long sinThetaRounded = Math.round(sinTheta);
            array[j] = Math.toIntExact(sinThetaRounded);
        }
    }

    public void parallelWithOnlyThreads(final int[] array) {
        Thread[] threads = new Thread[N_THREADS - 1];
        final int segmentLen = array.length / N_THREADS;
        int offset = 0;
        for (int i = 0; i < N_THREADS - 1; i++) {
            final int from = offset;
            final int to = offset + segmentLen;
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

        for (int i = 0; i < N_THREADS - 1; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignore) {
            }
        }
    }

    public void parallelWithThreadPool(final int[] array) {
        ExecutorService exec = Executors.newFixedThreadPool(N_THREADS - 1);
        final int segmentLen = array.length / N_THREADS;
        int offset = 0;
        for (int i = 0; i < N_THREADS - 1; i++) {
            final int from = offset;
            final int to = offset + segmentLen;
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

    public void parallelWithForkJoinPool(final int[] array) {
        ForkJoinPool pool = new ForkJoinPool(N_THREADS);
        pool.invoke(new ForEach(array, 0, array.length));
    }

    class ForEach extends RecursiveAction {
        private int[] array;
        private int from;
        private int to;

        // In real world, don't have it below 10,000
        public static final int TASK_LEN = 2;

        public ForEach(int[] array, int from, int to) {
            this.array = array;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            int len = to - from;
            if (len < TASK_LEN) {
                getSinTable(array, from, to);
            } else {
                int mid = (from + to) / 2;
                new ForEach(array, from, mid).fork();
                new ForEach(array, mid, to).fork();
            }
        }
    }

    public int[] declarativeParallelLoop() {
        return IntStream.iterate(0, i -> i + 1).limit(10)
                .parallel()
                .map(ele -> ele * 10)
                .mapToDouble(Math::sin)
                .mapToLong(Math::round)
                .mapToInt(Math::toIntExact)
                .toArray();
    }

    public void test() {
        int[] resultArr = new int[10];

        getSinTable(resultArr, 0, resultArr.length);
        print(resultArr);

        clear(resultArr);
        parallelWithOnlyThreads(resultArr);
        print(resultArr);

        clear(resultArr);
        parallelWithThreadPool(resultArr);
        print(resultArr);

        clear(resultArr);
        parallelWithForkJoinPool(resultArr);

        clear(resultArr);
        resultArr = declarativeParallelLoop();
        print(resultArr);
    }

    private void clear(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    private void print(int[] array) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(array, 0, 10)));
    }

    public static void main(String[] args) {
        new ParallelLoop().test();
    }
}