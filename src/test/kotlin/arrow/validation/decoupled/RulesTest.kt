/* gakshintala created on 3/16/20 */
package arrow.validation.decoupled

import org.junit.jupiter.api.Test

class RulesTest {

    @Test
    fun `fail fast`() {
        val failFastResult = Rules.failFast<ValidationError>().run {
            tupledN(
                    validateEmailWithRules("nowhere.com"),
                    validateEmailWithRules("nowheretoolong${(0..251).map { "g" }}")
            )
        }
        println(failFastResult)
    }

    @Test
    fun `Error Accumulation`() {
        val accResult = Rules.accumulateErrors<ValidationError>().run {
            tupledN( // This is required only in case of validating for multiple emails.
                    validateEmailWithRules("nowhere.com"),
                    validateEmailWithRules("nowheretoolong${(0..251).map { "g" }}")
            )
        }
        println(accResult)
    }
}
