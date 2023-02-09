package ga.overfullstack

import deepFlattenList
import org.junit.jupiter.api.Test

class DeepFlattenTest {

  @Test
  fun `flatten mix`() {
    val mixList = listOf("a", listOf("b", "c"), "d", listOf(listOf("e"), "f"))
    println(deepFlattenList(mixList))
  }
}
