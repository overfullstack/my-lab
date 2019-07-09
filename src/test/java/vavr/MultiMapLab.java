package vavr;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashMultimap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class MultiMapLab {
    private static List<Tuple2<String, String>> beans;
    private static List<Tuple2<String, Integer>> beansNum;
    private static List<Tuple2<String, String>> nuts;
    private static Map<String, String> beanMap;

    @BeforeAll
    static void setUp() {
        beans = List.of(
                Tuple.of("bean1", "y"),
                Tuple.of("bean2", "y"),
                Tuple.of("bean3", "x"),
                Tuple.of("bean4", "x"),
                Tuple.of("bean5", "y"),
                Tuple.of("bean6", "z"));
        beansNum = List.of(
                Tuple.of("bean1", 1),
                Tuple.of("bean2", 2),
                Tuple.of("bean3", 3),
                Tuple.of("bean4", 4),
                Tuple.of("bean5", 5),
                Tuple.of("bean6", 6));
        nuts = List.of(
                Tuple.of("nut1", "y"),
                Tuple.of("nut2", "y"),
                Tuple.of("nut3", "x"),
                Tuple.of("nut1", "a"),
                Tuple.of("nut2", "b"),
                Tuple.of("nut3", "c"));
        beanMap = HashMap.ofEntries(beans);
    }

    @Test
    void multiMapFromTuples() {
        var tupleList = List.of(
                Tuple.of("foo", "x"),
                Tuple.of("foo", "y"),
                Tuple.of("foo", "y"), // Duplicate values are retained.
                Tuple.of("bar", "x"),
                Tuple.of("bar", "x"),
                Tuple.of("bar", "y"),
                Tuple.of("bar", "z"));
        Multimap<String, String> multiMap =
                HashMultimap.withSeq().ofEntries(tupleList);

        System.out.println(multiMap.getOrElse("bar", List.empty()).toSet());
    }

    @Test
    void merge() {
        Multimap<String, String> beanMultiMap = HashMultimap.withSeq().ofEntries(beans);
        Multimap<String, Integer> beansNumMultiMap = HashMultimap.withSeq().ofEntries(beansNum);
    }

    @Test
    void zip() {
        Multimap<String, String> beanMultiMap = HashMultimap.withSeq().ofEntries(beans);
        Multimap<String, Integer> nutsMultiMap = HashMultimap.withSeq().ofEntries(beansNum);
        final var zip = beanMultiMap.zip(nutsMultiMap);
        //zip.forEach(System.out::println);
        final var tuple2s = zip.groupBy(tuple2 -> tuple2._1._1).mapValues(vals -> vals.map(val -> Tuple.of(val._1._2, val._2._2)));
    }

    @Test
    void zipWith() {
        Multimap<String, String> beanMultiMap = HashMultimap.withSeq().ofEntries(beans);
        Multimap<String, Integer> nutsMultiMap = HashMultimap.withSeq().ofEntries(beansNum);
        beanMultiMap.zipWith(nutsMultiMap, (tuple21, tuple22) -> Tuple.of(tuple21._1, tuple21._2, tuple22._2)).forEach(System.out::println);
    }
    
    @Test
    void zipMapAndMultiMap() {
        Multimap<String, String> beanMultiMap = HashMultimap.withSeq().ofEntries(beans);
        final var map = beanMultiMap.map((key, value) -> Tuple.of(key, Tuple.of(value, beanMap.getOrElse(key, ""))));
        System.out.println(map);
    }
    
    @Test
    void toMap(){
        Multimap<String, String> nutsMultiMap = HashMultimap.withSet().ofEntries(nuts);
        System.out.println(nutsMultiMap);
        nutsMultiMap.values().forEach(System.out::println);
    }
    
    @Test
    void groupBy() {
        System.out.println(nuts.groupBy(Tuple2::_1));
    }
}
