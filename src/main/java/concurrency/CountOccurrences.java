/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CountOccurrences extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;

    private static final int SEQUENTIAL_THRESHOLD = 5;

    private final int[] data;
    private final int start;
    private final int end;
    private final int value;

    public CountOccurrences(int[] data, int start, int end, int value) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public CountOccurrences(int[] data, int value) {
        this(data, 0, data.length, value);
    }

    @Override
    protected Integer compute() {
        final var length = end - start;
        
        if (length < SEQUENTIAL_THRESHOLD) {
            var count = 0;
            for (var i = start; i < end; i++) {
                if (data[i] == value) {
                    count++;
                }
            }
            return count;
        }
        
        final var split = length / 2;
        final var left = new CountOccurrences(data, start, start + split, value);
        left.fork();
        final var right = new CountOccurrences(data, start + split, end, value);
        return right.compute() + left.join();
    }
    
    public static void main(String[] args) {
        Map<Integer, Integer> frequencyCount = new HashMap<>(); 
        final var dataSet = new int[100];
        var random = new Random();
        for (var i = 0; i < 100; i++) {
            dataSet[i] = random.nextInt(10);
        }
        final var pool = new ForkJoinPool(10);

        for(Integer value: dataSet) {
            final var finder = new CountOccurrences(dataSet, value);
             frequencyCount.put(value, pool.invoke(finder));
        }
        System.out.println(frequencyCount);
    }
}
