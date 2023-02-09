package ga.overfullstack

import org.junit.jupiter.api.Test

class CrossInline {
  @Test
  fun crossInline() {
    someFun(Int::toString, 3)
  }

  companion object {
    inline fun someFun(crossinline fn: (Int) -> String, value: Int) {
      fn(value)
    }
  }
}
