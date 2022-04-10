package ga.overfullstack;

import static arrow.core.NonEmptyListKt.nonEmptyListOf;
import static ga.overfullstack.Memoize.entityObjIdRandomGenerator;
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

  @Test
  void neList() {
    final var bean = new Bean();
    bean.setName("name");
    final var integers = nonEmptyListOf(1, 2, 3);
    System.out.println(integers);
    entityObjIdRandomGenerator.invoke("a");
    entityObjIdRandomGenerator.invoke(2);
    System.out.println(entityObjIdRandomGenerator.invoke("a"));
    System.out.println(entityObjIdRandomGenerator.invoke(2));
  }
}
