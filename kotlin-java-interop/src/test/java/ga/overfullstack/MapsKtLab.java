package ga.overfullstack;

import java.util.Map;
import kotlin.collections.MapsKt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapsKtLab {
  @Test
  @DisplayName("Maps merge")
  void mapsMerge() {
    final var map1 = Map.of("a", 1, "b", 2);
    final var map2 = Map.of("b", -2, "c", 3);
    System.out.println(MapsKt.plus(map1, map2));
    System.out.println(MapsKt.plus(map2, map1));
  }
}
