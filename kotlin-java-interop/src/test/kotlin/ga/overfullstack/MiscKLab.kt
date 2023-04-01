package ga.overfullstack

import org.junit.jupiter.api.Test

class MiscKLab {
  @Test
  fun `Function literals with receiver`() {
    println(calc(1, 2) { other -> plus(other) })
  }
}
