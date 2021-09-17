package ga.overfullstack;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

public class ApacheCommonsLab {

  @Test
  void stringUtilsLab1() {
    final var list = List.of("a", "b", "c", "d");
    final var map = Map.of("a", "b", "c", "d");
    System.out.println(StringUtils.join(list));
    System.out.println(StringUtils.join(map));
  }
}
