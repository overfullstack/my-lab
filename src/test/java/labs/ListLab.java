package labs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
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
}
