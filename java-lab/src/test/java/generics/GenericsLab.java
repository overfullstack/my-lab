package generics;

import org.junit.jupiter.api.Test;
import java.util.List;

class GenericsLab {
  @Test
  void genericsLab1() {
    List<? super String> strList = List.of(new Object());
    final var obj = strList.get(0);
  }
}

