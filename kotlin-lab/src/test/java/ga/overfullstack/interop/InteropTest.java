package ga.overfullstack.interop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InteropTest {
  @Test
  @DisplayName("Function literals with receiver")
  void functionLiteralsWithReceiver() {
    System.out.println(MiscKt.calc(1, 2, (a, b) -> (int) Math.pow(a + b, 2)));
    System.out.println(MiscKt.nameLen(new Bean("bean"), Bean::nameLen));
  }

  @Test
  @DisplayName("Call Context receiver fun")
  void callContextReceiver() {
    MiscKt.contextFun(new Env(), "someStr");
  }
}
