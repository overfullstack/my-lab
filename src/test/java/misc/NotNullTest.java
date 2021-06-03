package misc;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class NotNullTest {
  @Test
  void notNull() {
      //someFun(null);
      var list = new ArrayList<String>();
      list.add("");
      list.add(null);
      NotNullKt.someFun(list);
  }

  void someFun(@NotNull String notnull) {
    System.out.print(notnull);
  }
}
