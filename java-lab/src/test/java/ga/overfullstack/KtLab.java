package ga.overfullstack;

import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KtLab {
  @Test
  @DisplayName("append to null")
  void appendToNull() {
    final var list = CollectionsKt.plus(null, "a");
    Assertions.assertNotNull(list);
  }

  @Test
  @DisplayName("Merge Maps with plus")
  void mergeMapsWithPlus() {
    final var map1 = Map.of("a", "1", "b", "21");
    final var map2 = Map.of("b", "22", "c", "3");
    System.out.println(String.join(",", MapsKt.plus(map1, map2).values()));
    System.out.println(String.join(",", MapsKt.plus(map2, map1).values()));
  }
}
