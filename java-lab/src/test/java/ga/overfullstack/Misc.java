package ga.overfullstack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Misc {
  @Test
  void exceptionInCatch() {
    someThrowingFun();
  }

  @Test
  @DisplayName("Casting")
  void casting() {
    final String a = null;
    final String b = (String) a;
    System.out.println(b);
  }

  void someThrowingFun() {
    try {
      throw new IllegalArgumentException("test");
    } catch (Exception e) {
      System.out.println("something");
      throw e;
    } finally {
      System.out.println("finally - someFun");
    }
  }

  @Test
  void testIntCast() {
    Object obj = null;
    final var i = (int) Objects.requireNonNullElse(obj, 0);
    System.out.println(i);
    add(1, 2, System.out::println);
    add(1, 2, result -> assertEquals(3, result));
  }

  @Test
  @DisplayName("String Join")
  void stringJoin() {
    System.out.println(String.join("', '", List.of("a", "b", "c")));
  }

  static void add(int a, int b) {
    System.out.println(a + b);
  }

  static void add(int a, int b, Consumer<Integer> consumer) {
    consumer.accept(a + b);
  }
}
