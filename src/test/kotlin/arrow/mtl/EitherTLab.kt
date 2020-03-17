/* gakshintala created on 3/17/20 */
package arrow.mtl

import arrow.core.left
import arrow.core.right
import arrow.fx.IO
import arrow.fx.IOPartialOf
import arrow.fx.extensions.io.monad.monad
import arrow.fx.fix
import arrow.mtl.extensions.eithert.monad.monad
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EitherTLab {

    @Test
    fun `short circuit`() {
        val eitherWithIO = EitherT.monad<IOPartialOf<Nothing>, String>(IO.monad()).fx.monad {
            val x = !EitherT(iOEither())
            val y = !EitherT(IO.just("rightY".right()))
            x + y
        }.value().fix()

        val result = eitherWithIO.unsafeRunSyncEither()

        result.fold({}, {
            assertTrue(it.isLeft())
            it.fold({ str ->
                assertEquals("leftX", str)
            }, {})
        })
    }

    private fun sendFalse() = false
    private fun iOEither() = IO.just(
            if (sendFalse()) "rightX".right() else "leftX".left()
    )
}
