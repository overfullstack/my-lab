package misc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ListTest {

  @Test
  void mutateList() {
    final var mutableList = Arrays.asList("a", "b", "c");
    mutateList(mutableList);
  }

  @Test
  void transformList() {
    var immutableList = List.of("a", "b", "c");
    transformList(immutableList);
    mutateList(immutableList); // Throws UnsupportedOperationException ⛔️
  }

  @Test
  void transformMap() {
    var map = Map.of(1, "a", 2, "b", 3, "c");
    map =
        map.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toUpperCase()));
    System.out.println(map);
  }

  List<String> mutateList(List<String> list) {
    for (var i = 0; i < list.size(); i++) {
      list.set(i, list.get(i).toUpperCase());
    }
    return list;
  }

  List<String> transformList(List<String> list) {
    return list.stream().map(String::toUpperCase).toList();
  }
}
