package concurrency;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ParallelLoopWithTimeStamps {

    private static final int NTHREADS = Runtime.getRuntime().availableProcessors();

    private void workToFindSin(int[] array, int from, int to) {
        for (var j = from; j < to; j++) {
            array[j] = Math.toIntExact(Math.round(Math.sin(j * 10d)));
        }
    }

    public void attempt1(final int[] array) {
        var threads = new Thread[NTHREADS - 1];
        final var segmentLen = array.length / NTHREADS;
        var offset = 0;
        for (var i = 0; i < NTHREADS - 1; i++) {
            final var from = offset;
            final var to = offset + segmentLen;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    workToFindSin(array, from, to);
                }
            });
            threads[i].start();
            offset += segmentLen;
        }
        // do last segment on main thread
        workToFindSin(array, array.length - segmentLen, array.length);

        // wait for completion
        for (var i = 0; i < NTHREADS - 1; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignore) {
            }
        }
    }

    public void attempt2(final int[] array) {
        var exec = Executors.newFixedThreadPool(NTHREADS - 1);
        final var segmentLen = array.length / NTHREADS;
        var offset = 0;
        for (var i = 0; i < NTHREADS - 1; i++) {
            final var from = offset;
            final var to = offset + segmentLen;
            exec.execute(new Runnable() {

                @Override
                public void run() {
                    workToFindSin(array, from, to);
                }
            });
            offset += segmentLen;
        }
        // do last segment on main thread
        workToFindSin(array, array.length - segmentLen, array.length);

        // wait for completion
        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignore) {
        }
    }

    class ForEach extends RecursiveAction {

        private final int[] array;
        private final int from;
        private final int to;

        // you can fine-tune this,
        // should be sth between 100 and 10000
        public final static int TASK_LEN = 2;

        public ForEach(int[] array, int from, int to) {
            this.array = array;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            var len = to - from;
            if (len < TASK_LEN) {
                workToFindSin(array, from, to);
            } else {
                // split workToFindSin in half, execute sub-tasks asynchronously
                var mid = (from + to) >>> 1;
                new ForEach(array, from, mid).fork();
                new ForEach(array, mid, to).fork();
            }
        }
    }

    public void attempt3(final int[] array) {
        var pool = new ForkJoinPool(NTHREADS);
        // blocks until completion
        pool.invoke(new ForEach(array, 0, array.length));
    }

    public int[] attempt4() {
        return IntStream.iterate(0, i -> i + 1).limit(10)
                .parallel()
                .map(ele -> ele * 10)
                .mapToDouble(Math::sin)
                .mapToLong(Math::round)
                .mapToInt(Math::toIntExact)
                .toArray();
    }

    public void test() {
        final var ROUNDS = 10;
        long seq = 0, a1 = 0, a2 = 0, a3 = 0, a4 = 0, t;

        var resultArr = new int[10];

        for (var i = 0; i < ROUNDS; i++) {
            t = System.currentTimeMillis();
            workToFindSin(resultArr, 0, resultArr.length);
            seq += System.currentTimeMillis() - t;
        }
        seq /= ROUNDS;
        print(resultArr);

        clear(resultArr);
        for (var i = 0; i < ROUNDS; i++) {
            t = System.currentTimeMillis();
            attempt1(resultArr);
            a1 += System.currentTimeMillis() - t;
        }
        a1 /= ROUNDS;
        print(resultArr);

        clear(resultArr);
        for (var i = 0; i < ROUNDS; i++) {
            t = System.currentTimeMillis();
            attempt2(resultArr);
            a2 += System.currentTimeMillis() - t;
        }
        a2 /= ROUNDS;
        print(resultArr);

        clear(resultArr);
        for (var i = 0; i < ROUNDS; i++) {
            t = System.currentTimeMillis();
            attempt3(resultArr);
            a3 += System.currentTimeMillis() - t;
        }
        a3 /= ROUNDS;
        print(resultArr);

        clear(resultArr);
        for (var i = 0; i < ROUNDS; i++) {
            t = System.currentTimeMillis();
            resultArr = attempt4();
            a4 += System.currentTimeMillis() - t;
        }
        a4 /= ROUNDS;
        print(resultArr);

        System.out.println("sequential avg: " + seq + " ms");
        System.out.println("attempt 1 avg: " + a1 + " ms");
        System.out.println("attempt 2 avg: " + a2 + " ms");
        System.out.println("attempt 3 avg: " + a3 + " ms");
        System.out.println("attempt 4 avg: " + a4 + " ms");
    }

    private void clear(int[] array) {
        for (var i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    private void print(int[] array) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(array, 0, 10)));
    }

    public static void main(String[] args) {
        new ParallelLoopWithTimeStamps().test();
    }
}