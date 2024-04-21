package ga.overfullstack;

import com.google.common.base.Throwables;
import org.junit.jupiter.api.Test;

class ExceptionLab {
  @Test
  void testCatches() {
    try {
      someFunThrows();
    } catch (Throwable e) {
      throw new RuntimeException(Throwables.getRootCause(e).toString(), e);
    } finally {
      System.out.println("Finally");
    }
  }

  @Test
  void testWrapper() {
    final var rte = new RuntimeException(new MyException("my-exception"));
    System.out.printf(String.valueOf(rte.getCause()));
  }

  static void someFunThrows() throws Throwable {
    throw new NullPointerException();
  }

  private static class MyException extends Exception {
    public MyException(String msg) {
      super(msg);
    }
  }
}
