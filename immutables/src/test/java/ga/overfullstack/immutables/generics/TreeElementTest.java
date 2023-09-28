package ga.overfullstack.immutables.generics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TreeElementTest {
  @Test
  @DisplayName("tree element")
  void treeElement() {
    TreeElement<String> tree =
        ImmutableNode.<String>builder()
            .addElements(ImmutableLeaf.of("A"))
            .addElements(ImmutableLeaf.of("B"))
            .addElements(
                ImmutableNode.<String>builder()
                    .addElements(ImmutableLeaf.of("C"))
                    .addElements(ImmutableLeaf.of("D"))
                    .build())
            .build();
    System.out.println(tree);
  }
}
