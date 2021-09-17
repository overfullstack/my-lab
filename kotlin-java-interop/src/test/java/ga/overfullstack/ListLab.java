package ga.overfullstack;

import static kotlin.collections.CollectionsKt.chunked;

import java.util.List;
import org.junit.jupiter.api.Test;

class ListLab {
  @Test
  void chunking() {
    final var list = List.of(1, 2, 3, 4, 5);
    System.out.println(chunked(list, 2));
  }

  @Test
  void crossInline() {
    CrossInline.Companion.someFun(i -> {
      i++;
      return i.toString();
    }, 3);
  }
}
