package vavr;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionLab {
    @Test
    void booleanOption() {
        Assertions.assertEquals(Option.some(false), Option.of(false));
        // These are not same.
        Assertions.assertNotSame(Option.some(false), Option.of(false));
    }
}
