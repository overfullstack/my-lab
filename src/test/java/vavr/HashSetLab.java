package vavr;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.Test;

public class HashSetLab {

    @Test
    void isImmutable() {
        var set = HashSet.of("Red", "Green", "Blue");
        var newSet = set.add("Yellow");

        // Hashset is immutable
        System.out.println(set);
        System.out.println(newSet);
    }
}
