package vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

public class TryLab {
    @Test
    void testIsTryLazy() {
        Try<Try<String>> tryNotLazy = Try.of(() -> {
            System.out.println("Try is not Lazy");
            return Try.of(() -> {
                System.out.println("Try inside is not Lazy");
                return "I m Tried and printed";
            });
        });
        System.out.println(tryNotLazy);
        System.out.println("Both Try's execute before Calling get() on Try");
        tryNotLazy.get();
    }
    
    @Test
    void tryWithException() {
        final Try<String> i_threw_it = Try.of(() -> {
            if (true) throw new RuntimeException("I threw it");
            return "try";
        }).orElse(Try.failure(new RuntimeException("I threw it in or else")));
        
        // Directly calling get() on try that throws exception also throws exception
        System.out.println(i_threw_it);
        i_threw_it.onFailure(cause -> System.out.println(cause.getMessage()));
    }

    @Test
    void tryWithInnerException() {
        Try<String> outerTry = Try.of(() -> {
            System.out.println("Try outside");
            final Try<String> innerTry = Try.of(() -> {
                System.out.println("Try inside");
                if (true) throw new RuntimeException("I threw it");
                return "I m Tried and printed";
            });
            return innerTry.get();
        });
        System.out.println(outerTry.getOrElseGet(Throwable::getMessage));
    }
}
