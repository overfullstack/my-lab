package labs;

import common.Bean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * gakshintala created on 7/15/20.
 */
public class ReflectionsLab {
    @Test
    void printFields() {
        Arrays.stream(Bean.class.getDeclaredFields()).map(Field::getName).forEach(System.out::println);
    }
}
