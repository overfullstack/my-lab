package labs;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.Bean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class StreamLab {
    private static List<Bean> beans;

    @BeforeAll
    static void setUp() {
        beans = new ArrayList<>();
        var bean1 = new Bean("1", "a", null, Collections.emptyList());
        var bean2 = new Bean("2", "b", null, Collections.emptyList());
        var bean3 = new Bean("3", "c", null, Collections.emptyList());
        beans.add(bean1);
        beans.add(bean2);
        beans.add(bean3);
    }

    @Test
    void mapAndFlatMap() {
        beans = new ArrayList<>();
        var mapped = beans.stream().map(bean -> bean.getBeans().stream());
        var flatMapped = beans.stream().flatMap(bean -> bean.getBeans().stream());
    }

    @Test
    void generate() {
        Collections.nCopies(8, 1).stream().forEach(System.out::print);
        System.out.println();
        IntStream.generate(() -> 1).limit(8).forEach(System.out::print);
    }

    @Test
    void findFirstTest() {
        Integer[] arr = {1, 2, 3, 6};
        Arrays.stream(arr).filter(i -> i > 5).map(Optional::of).findFirst();
    }

    @Test
    void findFirstForEmpty() {
        System.out.println(new ArrayList<>().stream().findFirst());
    }

    @Test
    void lazyStreaming() {
        Stream.iterate(0, i -> i + 1)
                .flatMap(i -> Stream.of(i, i, i, i))
                .map(i -> i + 1)
                .peek(i -> System.out.println("Map: " + i))
                .limit(5)
                .forEach(i -> {
                });

        System.out.println();
        System.out.println();

        Stream.iterate(0, i -> i + 1)
                .flatMap(i -> Stream.of(i, i, i, i))
                .limit(5)
                .map(i -> i + 1)
                .peek(i -> System.out.println("Map: " + i))
                .forEach(i -> {
                });
    }

    @Test
    void reduceOrder() {
        beans.stream().reduce((prev, cur) -> {
            System.out.println("Prev: " + prev);
            System.out.println("Cur: " + cur);
            return cur;
        });
    }

}
