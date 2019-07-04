package vavr;

import common.Bean;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.function.Function;

public class CollectionsLab {

    private static List<Bean> beans;

    @BeforeAll
    static void setUp() {
        beans = List.of(
                new Bean("1", "a", Collections.emptyList()),
                new Bean("2", "b", Collections.emptyList()),
                new Bean("3", "c", Collections.emptyList())
        );
    }

    @Test
    void foldApply() {
        List<Function<Bean, Bean>> functionList = List.of(
                bean -> {bean.setProp1(bean.getProp1() + ":11"); return bean;},
                bean -> {bean.setProp1(bean.getProp1() + ":22"); return bean;},
                bean -> {bean.setProp1(bean.getProp1() + ":33"); return bean;}
        );
        var bean = new Bean("1", "A", Collections.emptyList());
        final var resultBean = functionList.foldLeft(bean, (acc, cur) -> cur.apply(acc));
        System.out.println(resultBean);
    }

    @Test
    void listFoldLeft() {
        final var result = beans.foldLeft("start", (acc, cur) -> {
            System.out.println("acc: " + acc);
            System.out.println("cur: " + cur);
            return acc + " - " + cur;
        });
        System.out.println(result);
    }

    @Test
    void listReduce() {
        beans.reduce((prev, cur) -> {
            System.out.println("prev: " + prev);
            System.out.println("cur: " + cur);
            return cur;
        });
    }
}
