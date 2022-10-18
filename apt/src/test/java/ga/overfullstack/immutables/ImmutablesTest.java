package ga.overfullstack.immutables;

import org.junit.jupiter.api.Test;

class ImmutablesTest {
  @Test
  void person() {
    final var person = Person.configure().age(33).isEmployed(false).done();
    System.out.println(person);
  }
}
