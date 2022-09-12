package ga.overfullstack.lombok

import org.junit.jupiter.api.Test

class LombokTest {
  
  @Test
  fun lombokTest() {
    val somePojo = SomePojo.builder().age(1).name("one").build()
    println(somePojo)
  }
}
