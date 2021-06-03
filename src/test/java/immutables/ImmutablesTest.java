package immutables;

import io.vavr.CheckedFunction1;
import org.junit.jupiter.api.Test;

class ImmutablesTest {
    @Test
    void headerConfig() {
        ImmutablePerson.builder()
                .age(33)
                .isEmployed(false)
                .addName("Billy Bounce")
                .build();
    }
}
