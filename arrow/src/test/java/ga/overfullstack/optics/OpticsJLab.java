package ga.overfullstack.optics;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OpticsJLab {
  @Test
  @DisplayName("Optics Lab")
  void opticsLab() {
    final var me =
        new Person(
            "Gopal",
            99,
            new Address(
                new Street("Kotlinstraat", 1),
                new City("Hilversum", "Netherlands"),
                List.of(1, 2)));
  }
}
