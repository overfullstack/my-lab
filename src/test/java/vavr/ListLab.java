package vavr;

import common.Bean;
import common.Nut;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class ListLab {

    private static List<Bean> beans;
    private static List<Nut> nuts;
    private static List<Bean> vavrBeans;
    private static java.util.List<Bean> javaBeans;
    private static java.util.List<Bean> javaBeansArrayList;

    @BeforeAll
    static void setUp() {
        beans = List.of(
                new Bean("1", "a", Collections.emptyList()),
                new Bean("2", "b", Collections.emptyList()),
                new Bean("3", "c", Collections.emptyList()),
                new Bean("1", "x", Collections.emptyList()),
                new Bean("2", "y", Collections.emptyList()),
                new Bean("3", "z", Collections.emptyList())
        );
        nuts = List.of(
                new Nut("n1", "a", Collections.emptyList()),
                new Nut("n2", "b", Collections.emptyList()),
                new Nut("n3", "c", Collections.emptyList())
        );
        vavrBeans = List.of(
                new Bean("v1", "a", Collections.emptyList()),
                new Bean("v2", "b", Collections.emptyList()),
                new Bean("v3", "c", Collections.emptyList())
        );
        javaBeans = java.util.List.of(
                new Bean("1", "a", Collections.emptyList()),
                new Bean("2", "b", Collections.emptyList()),
                new Bean("3", "c", Collections.emptyList())
        );

        javaBeansArrayList = new ArrayList<>();
        var bean1 = new Bean("1", "a", Collections.emptyList());
        var bean2 = new Bean("2", "b", Collections.emptyList());
        var bean3 = new Bean("3", "c", Collections.emptyList());
        javaBeansArrayList.add(bean1);
        javaBeansArrayList.add(bean2);
        javaBeansArrayList.add(bean3);
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
    
    @Test
    void groupBy() {
        beans.groupBy(Bean::getProp1).forEach(System.out::println);
        System.out.println(beans.groupBy(Bean::getProp1).mapValues(value -> value.map(Bean::getProp2)));
    }
    
    @Test
    void toMap() {
        System.out.println(beans.toMap(Bean::getProp1, Bean::getProp2));
    }

    @Test
    void unZip() {
        //Tuple2<List<String>, List<String>> tuple2ForVavr = List.ofAll(vavrBeans).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
        //Tuple2<List<String>, List<String>> tuple2ForJava = List.ofAll(javaBeans).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
        Tuple2<List<String>, List<String>> tuple2ForJavaArrayList = List.ofAll(javaBeansArrayList).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
    }
    
    @Test
    void zip() {
        final var zip = beans.zip(nuts);
        System.out.println(zip.size());
        System.out.println(zip);
    }
    
    @Test
    void foldLeft() {
        beans.map(bean -> nuts.foldLeft(List.of(), (acc, nut) -> acc.append(bean.getProp1() + nut.getProp2()))).forEach(System.out::println);
    }
    
}
