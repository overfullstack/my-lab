/* gakshintala created on 8/3/20 */
package arrow

import arrow.fx.coroutines.parTraverse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TraverseLab {
    @Test
    fun `Parallel Traverse`(): Unit = runBlocking {
        (1..5).parTraverse {
            delay(500)
            println("${Thread.currentThread().name} - $it")
        }
    }
}
