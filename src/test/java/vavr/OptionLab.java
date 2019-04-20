package vavr;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionLab {
    @Test
    void booleanOption() {
        Assertions.assertSame(Option.some(false), Option.of(false));
    }
}
