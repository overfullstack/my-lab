package labs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * gakshintala created on 11/21/19.
 */
public class ListLab {

    @Test
    void addToList() {
        final var list = List.of(1);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add(2));
    }

    @Test
    void concatUnmodifiableLists() {
        Stream.of(List.of(1), List.of(2)).flatMap(Collection::stream).forEach(System.out::println);
    }

    @Test
    void filterListWithMap() {
        var list = List.of(1, 2, 3);
        var map = Map.of(1, "one", 2, "two", 4, "four");
        System.out.println(list.stream()
                .filter(map::containsKey)
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(","))
        );
    }
}
