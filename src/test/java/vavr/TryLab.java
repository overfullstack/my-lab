package vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

public class TryLab {
    @Test
    void testIsTryLazy() {
        Try<Object> tryNotLazy = Try.of(() -> {
            System.out.println("Try is not Lazy");
            return Try.of(() -> {
                System.out.println("Try inside is not Lazy");
                return null;
            });
        });
        // Nothing happens with this call.
        System.out.println("Both Try's execute before Calling get() on Try");
        tryNotLazy.get();
    }
}
