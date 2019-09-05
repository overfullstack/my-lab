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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

class StreamLab {
    private static List<Bean> beans;

    @BeforeAll
    static void setUp() {
        beans = List.of(
                new Bean("1", "a", "A"),
                new Bean("1", "b", "B"),
                new Bean("2", "a", "C"),
                new Bean("2", "b", "D"),
                new Bean("3", "c", "E"),
                new Bean("4", "c", "F")
        );
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

    @Test
    void nestedGrouping() {
        beans.stream().collect(groupingBy(Bean::getProp, groupingBy(Bean::getProp2)))
                .entrySet().forEach(System.out::println);
    }

    @Test
    void multiGroupingImperative() {
        var prop1Group = new HashMap<String, Set<Bean>>();
        var prop2Group = new HashMap<String, Set<Bean>>();
        for (var bean : beans) {
            prop1Group.computeIfAbsent(bean.getProp1(), key -> new HashSet<>()).add(bean);
            prop2Group.computeIfAbsent(bean.getProp2(), key -> new HashSet<>()).add(bean);
        }
    }

    @Test
    void multiGroupingDeclarative() {
        var prop1Group = beans.stream().collect(groupingBy(Bean::getProp1));
        var prop2Group = beans.stream().collect(groupingBy(Bean::getProp2));
    }

    @Test
    void teeing() {
        beans.stream().collect(
                Collectors.teeing(
                        groupingBy(Bean::getProp1),
                        groupingBy(Bean::getProp2),
                        List::of))
                .forEach(map -> map.entrySet().forEach(System.out::println));
    }
}
