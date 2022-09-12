package ga.overfullstack.lombok;

import java.util.List;
import ga.overfullstack.lombok.customsuperbuilder.Manager;
import org.junit.jupiter.api.Test;

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
