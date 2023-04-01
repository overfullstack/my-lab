package ga.overfullstack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ga.overfullstack.MiscKt.calc;

class MiscJLab {
  @Test
  @DisplayName("Function literals with receiver")
  void functionLiteralsWithReceiver() {
    System.out.println(calc(1, 2, (a, b) -> (int) Math.pow(a + b, 2)));
  }
}
