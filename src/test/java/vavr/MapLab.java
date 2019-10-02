package vavr;/* gakshintala created on 9/6/19 */

import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MapLab {
    Map<String, String> beanMap;

    @BeforeAll
    void setUp() {
        beanMap = HashMap.ofEntries(List.of(
                Tuple.of("bean1", "a"),
                Tuple.of("bean1", "b"),
                Tuple.of("bean1", "c"),
                Tuple.of("bean2", "y"),
                Tuple.of("bean3", "x"),
                Tuple.of("bean4", "x"),
                Tuple.of("bean5", "y"),
                Tuple.of("bean6", "z")));
    }


    @Test
    void toMapWithIterator() {
        beanMap.iterator(Tuple::of);
    }
}
