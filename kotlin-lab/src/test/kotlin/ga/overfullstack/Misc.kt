package ga.overfullstack

import io.kotest.core.spec.style.StringSpec
import java.util.Optional

class Misc : StringSpec({
  "testOptionalGroupBy" {
    val list = listOf(Optional.of("a"), Optional.of("a"), Optional.of("c"))
    val group = list.groupBy { it }
    println(group)
  }
})
