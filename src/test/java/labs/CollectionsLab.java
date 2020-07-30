package labs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class CollectionsLab<T> {
    @Test
    void toArray() {
        System.out.println(Arrays.toString(List.of("a", "b", "c").toArray(new String[0])));
    }
    
    @Test
    void sort() {
        final var list = new ArrayList<>(List.of(3, 7, 1, 2));
        Collections.sort(list);
        System.out.println(list);
        
        String abc = null;
        abc.length();
    }
    
    @Test
    void generics() {
        final var list = new ArrayList<T>();
        Assertions.assertTrue(list.isEmpty());
    }
}
