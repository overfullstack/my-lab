package ga.overfullstack;

import ga.overfullstack.customsuperbuilder.Manager;
import org.junit.jupiter.api.Test;
import java.util.List;

class CustomSuperBuilderTest {

  @Test
  void testCustomBuilder() {
    final var manager = Manager.<String>builder()
        .id("001")
        .files(List.of("file"))
        .build();
    System.out.println(manager);
  }

}
