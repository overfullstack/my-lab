package ga.overfullstack.immutables;

import ga.overfullstack.immutable.Kick;
import ga.overfullstack.immutable.StepConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImmutablesTest {
  @Test
  void person() {
    final var person = Person.configure().age(33).isEmployed(false).isMarried(true).done();
    System.out.println(person);
  }

  @Test
  @DisplayName("Expressive factory methods")
  void expressiveFactoryMethods() {
    final var point = Point.origin();
    System.out.println(point);
  }

  @Test
  @DisplayName("Kotlin Builder")
  void kotlinBuilder() {
    final var kick = Kick.configure()
        .stepConfig(StepConfig.configure().folder("").step("").off())
        .off();
    System.out.println(kick);
  }
}
