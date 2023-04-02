package ga.overfullstack

import ga.overfullstack.SuccessConfig.Companion.successType
import ga.overfullstack.SuccessConfig.Companion.validateIfSuccess
import org.junit.jupiter.api.Test

class SuccessConfigTest {
  @Test
  fun successConfig() {
    val sc = successType("abc")
    validateIfSuccess("abc", "pqr")
    
  }
}
