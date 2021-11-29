package ga.overfullstack;

import org.junit.jupiter.api.Test;
import java.util.Objects;

class Misc {
  @Test
  void exceptionInCatch() {
    try {
      someThrowingFun();
    } catch (Exception e) {
      throw new RuntimeException();
    } finally {
      System.out.println("finally");
    }
  }
  
  void someThrowingFun() {
    try {
      throw new RuntimeException();
    } finally {
      System.out.println("finally - someFun");
    }
  }

  @Test
  void testIntCast() {
    Object obj = null;
    final var i = (int) Objects.requireNonNullElse(obj, 0);
    System.out.println(i);
  }
}
