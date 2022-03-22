package ga.overfullstack;

public class ExceptionLab {
  public static void main(String[] args) {
    try {
      someFunThrows();
    } catch (Throwable e) {
      System.out.printf("caught " + e.getMessage());
    }
  }
  
  static void someFunThrows() throws Throwable {
    throw new MyException("my-exception");
  }
  
  private static class MyException extends Exception {
    public MyException(String msg) {
      super(msg);
    }
  }
}

