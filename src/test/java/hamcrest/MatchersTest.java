package hamcrest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.is;

class MatchersTest {
    @Test
    void someTest() {
        System.out.println(either(is("")).or(is("")).matches(1));
        System.out.println(either(is("1")).or(is("")).matches("1"));
    }
}
