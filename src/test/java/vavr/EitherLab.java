package vavr;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

/**
 * gakshintala created on 4/12/20.
 */
public class EitherLab {
    @Test
    void nullInEither() {
        Either<Integer, ?> left = Either.left(2);
        final Either<Integer, ?> left2 = Either.left(2);
        left = left2;
        
        System.out.println(Either.right("").map(ignore -> null)); // Either can hold null
    }
    
}
