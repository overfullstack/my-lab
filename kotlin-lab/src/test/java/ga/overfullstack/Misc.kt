package ga.overfullstack

import org.junit.jupiter.api.Test
import java.util.Optional

class Misc {
  @Test
  fun testOptionalGroupBy() {
    val list = listOf(Optional.of("a"), Optional.of("a"), Optional.of("c"))
    val group = list.groupBy { it }
    println(group)
  }
}
