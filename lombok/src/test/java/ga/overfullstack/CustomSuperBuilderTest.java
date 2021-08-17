package ga.overfullstack;

import ga.overfullstack.customsuperbuilder.Employee;
import ga.overfullstack.customsuperbuilder.Manager;
import org.junit.jupiter.api.Test;

class CustomSuperBuilderTest {

  @Test
  void testCustomBuilder() {
    Employee.<String>builder()
        .id("001")
        .build();
    Manager.<String>builder()
        .id("001")
        .build();
  }

}
