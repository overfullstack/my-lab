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
    void testMutationOfMapReference() {
        var map = new HashMap<Integer, MyClass>();
        map.put(1, new MyClass(1));
        var myClass = map.get(1);
        myClass.val++;
        System.out.println(map.get(1).val); // It mutates value inside map
    }

    @Test
    void mapMerge() {
        var map = new HashMap<Integer, List<MyClass>>();
        map.put(1, List.of(new MyClass(1)));
        final BiFunction<List<MyClass>, List<MyClass>, List<MyClass>> listCombiner = (oldList, newList) ->
                Stream.of(oldList, newList).flatMap(Collection::stream).collect(Collectors.toList());
        map.merge(2, List.of(new MyClass(2)), listCombiner);
        map.merge(2, List.of(new MyClass(2)), listCombiner);
        map.get(2).forEach(System.out::println);
    }

    @Data
    @AllArgsConstructor
    static class MyClass {
        int val;
    }
}

