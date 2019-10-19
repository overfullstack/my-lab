package labs;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.Bean;
import common.BeanType;
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

import static common.BeanType.T1;
import static common.BeanType.T12;
import static common.BeanType.T2;
import static java.util.stream.Collectors.groupingBy;

class StreamLab {
    private static List<Bean> beans = List.of(
            new Bean("1", "a", "A", T1),
            new Bean("1", "b", "B", T1),
            new Bean("2", "a", "C", T2),
            new Bean("2", "b", "D", T12),
            new Bean("3", "c", "E", T12),
            new Bean("4", "c", "F", T2));

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
    void reduceEmptyList() {
        Stream.<Integer>empty().reduce(Integer::sum);
    }

    @Test
    void reduce() {
        var listForSum = List.of(1, 2, 3, 4);
        listForSum.stream().reduce((a, b) -> {
            // a is a accumulator
            System.out.print(" " + a + " " + b + " ");
            return a + b;
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

    @Test
    void groupBy() {
        final var beansByType = beans.stream().collect(groupingBy(StreamLab::getType));
        beansByType.values().forEach(System.out::println);
    }

    private static BeanType getType(Bean bean) {
        return switch (bean.getType()) {
            case T1 -> T1;
            case T2 -> T2;
            case T12 -> T12;
        };
    }

    @Test
    void mapValidation() {
        var map = new HashMap<String, String>();
        map(map);
    }

    private String map(HashMap<String, String> map) {
        for (var entry : map.entrySet()) {
            if (entry.getKey().length() > 10) {
                return "invalid_key";
            } else if (entry.getValue().length() > 10) {
                return "invalid_value";
            } else if (entry.getKey().isEmpty()) {
                return "empty_key";
            } else if (entry.getValue().isEmpty()) {
                return "empty_value";
            }
        }
        return "all_valid";
    }
}
