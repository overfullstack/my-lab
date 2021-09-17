package ga.overfullstack;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

public class StackWalkerLab {
  private static StackWalker WALKER = StackWalker.getInstance(RETAIN_CLASS_REFERENCE);

  static Runnable runnable = () -> System.out.println(WALKER.getCallerClass());
  
  public static void someMethod() {
    System.out.println(WALKER.getCallerClass());
  }
}
