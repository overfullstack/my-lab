package vavr;

import common.Bean;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class ListLab {
    private static List<Bean> vavrBeans;
    private static java.util.List<Bean> javaBeans;
    private static java.util.List<Bean> javaBeansArrayList;

    @BeforeAll
    static void setUp() {
        vavrBeans = List.of(
                new Bean("1", "a", Collections.emptyList()),
                new Bean("2", "b", Collections.emptyList()),
                new Bean("3", "c", Collections.emptyList())
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
    void unZip() {
        //Tuple2<List<String>, List<String>> tuple2ForVavr = List.ofAll(vavrBeans).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
        //Tuple2<List<String>, List<String>> tuple2ForJava = List.ofAll(javaBeans).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
        Tuple2<List<String>, List<String>> tuple2ForJavaArrayList = List.ofAll(javaBeansArrayList).unzip(bean -> Tuple.of(bean.getProp1(), bean.getProp2()));
    }
}
