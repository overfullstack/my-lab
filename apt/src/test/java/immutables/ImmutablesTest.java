package immutables;

import io.vavr.CheckedFunction1;
import org.immutables.value.Value;
import org.junit.jupiter.api.Test;

import java.util.List;

class ImmutablesTest {
    @Test
    void headerConfig() {
        ImmutablePerson.builder()
                .age(33)
                .isEmployed(false)
                .addName("Billy Bounce")
                .build();
    }

    @Value.Style(stagedBuilder = true)
    @Value.Immutable
    interface Person {
        List<String> name();
        int age();
        boolean isEmployed();
    }
}
