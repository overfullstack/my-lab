package ga.overfullstack

import SuccessConfig.Companion.successType
import SuccessConfig.Companion.validateIfSuccess
import org.junit.jupiter.api.Test

class SuccessConfigTest {
  @Test
  fun successConfig() {
    val sc = successType("abc")
    validateIfSuccess("abc", "pqr")
    
  }
}
