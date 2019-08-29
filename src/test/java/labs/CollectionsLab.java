package labs;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CollectionsLab {
    @Test
    void toArray() {
        System.out.println(Arrays.toString(List.of("a", "b", "c").toArray(new String[0])));
    }
}
