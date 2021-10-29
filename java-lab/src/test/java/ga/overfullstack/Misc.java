package ga.overfullstack;

import org.junit.jupiter.api.Test;

class Misc {
  @Test
  void exceptionInCatch() {
    try {
      throw new RuntimeException();
    } catch (Exception e) {
      throw new RuntimeException();
    } finally {
      System.out.println("finally");
    }
  }
  
}
