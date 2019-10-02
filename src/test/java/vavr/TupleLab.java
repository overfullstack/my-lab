package vavr;/* gakshintala created on 10/1/19 */

import io.vavr.Tuple;
import org.junit.jupiter.api.Test;

public class TupleLab {

    @Test
    void map() {
        Tuple.of(1, 2).map(Tuple::of);
    }
}
