package vavr;

import common.Bean;
import common.Nut;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class IteratorLab {

    private static List<Bean> beans;
    private static Set<Bean> beansSet;
    private static List<Nut> nuts;
    private static List<Bean> vavrBeans;
    private static java.util.List<Bean> javaBeans;
    private static java.util.List<Bean> javaBeansArrayList;

    @BeforeAll
    static void setUp() {
        beans = List.of(
                new Bean("1", "a", Collections.emptyList()),
                new Bean("1", "a", Collections.emptyList()),
                new Bean("1", "b", Collections.emptyList()),
                new Bean("2", "b", Collections.emptyList()),
                new Bean("3", "c", Collections.emptyList()),
                new Bean("4", "c", Collections.emptyList()),
                new Bean("1", "x", Collections.emptyList()),
                new Bean("2", "y", Collections.emptyList()),
                new Bean("3", "z", Collections.emptyList())
        );
        beansSet = HashSet.of(
                new Bean("1", "a", Collections.emptyList()),
                new Bean("1", "a", Collections.emptyList()),
                new Bean("1", "b", Collections.emptyList()),
                new Bean("2", "b", Collections.emptyList()),
                new Bean("3", "c", Collections.emptyList()),
                new Bean("4", "c", Collections.emptyList()),
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
    void testLazy() {
        final var peek = beans.iterator().peek(System.out::println).filter(bean -> bean.getProp().equals("1"));
        System.out.println("Printed only after this, as Iterator is lazy");
        peek.get();
    }

    @Test
    void foldApply() {
        List<Function<Bean, Bean>> functionList = List.of(
                bean -> {
                    bean.setProp(bean.getProp() + ":11");
                    return bean;
                },
                bean -> {
                    bean.setProp(bean.getProp() + ":22");
                    return bean;
                },
                bean -> {
                    bean.setProp(bean.getProp() + ":33");
                    return bean;
                }
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
        /*beans.groupBy(Bean::getProp1).forEach(System.out::println);
        System.out.println(beans.groupBy(Bean::getProp1).mapValues(value -> value.map(Bean::getProp2)));
        beans.groupBy(bean -> Tuple.of(bean.getProp1(), bean.getProp2())).forEach(System.out::println);*/

        beansSet.groupBy(Bean::getProp).forEach(System.out::println);
    }

    @Test
    void toMap() {
        System.out.println(beans.toMap(Bean::getProp, Bean::getProp2));
    }

    @Test
    void unZip() {
        //Tuple2<List<String>, List<String>> tuple2ForVavr = List.ofAll(vavrBeans).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
        //Tuple2<List<String>, List<String>> tuple2ForJava = List.ofAll(javaBeans).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
        Tuple2<List<String>, List<String>> tuple2ForJavaArrayList = List.ofAll(javaBeansArrayList).unzip(bean -> Tuple.of(bean.getProp(), bean.getProp2()));
    }

    @Test
    void zip() {
        final var zip = beans.zip(nuts);
        System.out.println(zip.size());
        System.out.println(zip);
    }

    @Test
    void foldLeft() {
        beans.map(bean -> nuts.foldLeft(List.of(), (acc, nut) -> acc.append(bean.getProp() + nut.getProp2()))).forEach(System.out::println);
    }

    @Test
    void transform() {
        beans.iterator().transform(beans -> {
            System.out.println(beans.size());
            return beans.size();
        });
    }

    @Test
    void flatMap() {
        String[] charArr = {"a", "b", "c"};
        String[] numArr = {"1", "2", "3"};
        var listOfArr = List.of(charArr, numArr);
        System.out.println(listOfArr.flatMap(List::of).toSet());
    }

    @Test
    void zipWithPermutationsAndCombinations() {
        List.of(1, 2, 3).permutations().flatMap(perm -> List.of("a", "b", "c").zip(perm)).toSet().forEach(System.out::println);
    }

    @Test
    void filterNot() {
        beans = beans.filterNot(bean -> "1".equalsIgnoreCase(bean.getProp()));
        System.out.println(beans);
    }

    @Test
    void splitAt() {
        final var listListTuple2 = beans.splitAt(bean -> "3".equalsIgnoreCase(bean.getProp()));
        System.out.println(listListTuple2._1);
        System.out.println(listListTuple2._2);
    }

    @Test
    void partition() {
        final var listListTuple2 = beans.partition(bean -> "1".equalsIgnoreCase(bean.getProp()));
        System.out.println(listListTuple2._1);
        System.out.println(listListTuple2._2);
    }
}
