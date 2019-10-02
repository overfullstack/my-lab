package vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

public class TryLab {
    @Test
    void tryIsNotLazy() {
        var tryNotLazy = Try.of(() -> {
            System.out.println("Try is not Lazy");
            return Try.of(() -> {
                System.out.println("Try inside is not Lazy");
                return "I m Tried and printed";
            });
        }).map(ignore -> {
            System.out.println("map on Try is not lazy");
            return null;
        });
        System.out.println(tryNotLazy);
        System.out.println("Both Try's executed, before Calling get() on Try");
        tryNotLazy.get();
    }

    @Test
    void tryWithException() {
        final var i_threw_it = Try.of(() -> {
            if (true) throw new RuntimeException("I threw it");
            return "try";
        }).orElse(Try.failure(new RuntimeException("I threw it in or else")));
        // Directly calling get() on try that throws exception also throws exception
        // System.out.println(i_threw_it.get());
        System.out.println(i_threw_it);
        i_threw_it.onFailure(cause -> System.out.println(cause.getMessage()));
    }

    @Test
    void filterOnTryWithException() {
        final var i_threw_it = Try.of(() -> {
            if (true) throw new RuntimeException("I threw it");
            return true;
        }).filter(result -> {
            System.out.println("This won't be printed: " + result);
            return result;
        }).orElse(Try.failure(new RuntimeException("I threw it in or else")));
    }

    @Test
    void tryWithInnerException() {
        var outerTry = Try.of(() -> {
            System.out.println("Try outside");
            final var innerTry = Try.of(() -> {
                System.out.println("Try inside");
                if (true) throw new RuntimeException("I threw it");
                return "I m Tried and printed";
            });
            return innerTry.get();
        });

        // Outside Try holds inside try exception.
        System.out.println(outerTry.getOrElseGet(Throwable::getMessage));
    }
}
