package immutables;

import lombok.Value;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

class ImmutablesTest {
    @Test
    void headerConfig() {
        System.out.println(new Bean(List.of()));
        System.out.println(Point.toBuild().x("").y(0.0).prepare());
    }

    @Value
    private static class Bean {
        List<String> batch;
    }
}
