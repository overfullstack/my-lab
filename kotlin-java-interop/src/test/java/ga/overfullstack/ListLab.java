package ga.overfullstack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static kotlin.collections.CollectionsKt.chunked;

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
