package misc;

import static misc.NameKt.getAsName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ValueClassTest {

  @Test
  void testValueClass() {
    final var name = getAsName().get();
    Assertions.assertEquals("Kotlin", name.getTheName());
  }
}
