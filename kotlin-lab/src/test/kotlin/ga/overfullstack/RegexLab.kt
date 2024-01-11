package ga.overfullstack

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RegexLab {
  @Test
  fun `Only digits`() {
    "1234a".matches("\\d+".toRegex()) shouldBe false
    "1234".matches("\\d+".toRegex()) shouldBe true
  }
}
