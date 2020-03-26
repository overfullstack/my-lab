/* gakshintala created on 2/19/20 */
package arrow.io

import arrow.fx.IO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class Suspended {
    
    private fun getIO() = IO { Thread.currentThread().name }

    @Test
    fun `IO suspended`() = runBlocking { println(getIO().suspended()) }
    
    @Test
    fun `IO unsafe`() {
        println(getIO().unsafeRunSyncEither())
    }
}
