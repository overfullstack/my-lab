package labs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapLab {

    @Test
    void mapMerge() {
        var map = new HashMap<Integer, Integer>();
        map.put(1, 0);
        map.merge(1, 10, (oldValue, ignore) -> oldValue + 1); // 10 here would be ignore

        map.merge(2, 1, (ignore, val) -> val + 1);

        var newVal = map.getOrDefault(3, 0) + 1;
        map.put(3, newVal);
        System.out.println(map);
    }

    @Test
    void testMutationOfMapReference() {
        var map = new HashMap<Integer, MyClass>();
        map.put(1, new MyClass(1));
        var myClass = map.get(1);
        myClass.val++;
        System.out.println(map.get(1).val); // It mutates value inside map
    }

    @Test
    void mapMergeWithListCombiner() {
        var map = new HashMap<Integer, List<MyClass>>();
        map.put(1, List.of(new MyClass(1)));
        final BiFunction<List<MyClass>, List<MyClass>, List<MyClass>> listCombiner = (oldList, newList) ->
                Stream.of(oldList, newList).flatMap(Collection::stream).collect(Collectors.toList());
        map.merge(2, List.of(new MyClass(2)), listCombiner);
        map.merge(2, List.of(new MyClass(2)), listCombiner);
        map.get(2).forEach(System.out::println);
    }

    @Test
    void computeIfAbsentVsPutIfAbsent() {
        var map = new HashMap<Integer, List<MyClass>>();
        map.putIfAbsent(1, List.of());
        map.computeIfAbsent(2, ignore -> List.of());
        System.out.println(map.size());
    }

    @Data
    @AllArgsConstructor
    static class MyClass {
        int val;
    }
}

