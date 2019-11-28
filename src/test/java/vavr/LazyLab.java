package vavr;

import io.vavr.Lazy;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

public class LazyLab {
    @Test
    void testLazyWithTry() {
        var lazy = Lazy.of(() -> {
            var tryOf = Try.of(() -> {
                System.out.println("Try is not Lazy");
                return Try.of(() -> {
                    System.out.println("Try inside is not Lazy");
                    if (true) throw new RuntimeException("I threw RuntimeException");
                    return null;
                });
            });
            return tryOf.get();
        });
        System.out.println("Try prints after Executing Lazy");
        System.out.println(lazy.get().getCause());
    }
}
