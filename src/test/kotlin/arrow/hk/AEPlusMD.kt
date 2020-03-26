/* gakshintala created on 3/20/20 */
package arrow.hk

import arrow.core.Either
import arrow.core.EitherPartialOf
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.fx.IO
import arrow.fx.IOPartialOf
import arrow.fx.extensions.io.monadDefer.monadDefer
import arrow.fx.fix
import arrow.fx.typeclasses.MonadDefer
import arrow.typeclasses.ApplicativeError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

interface DeferValidator<F, S, E> : MonadDefer<F> {
    val AE: ApplicativeError<S, E>
}

class AEPlusMD {
    lateinit var deferValidator: DeferValidator<IOPartialOf<String>, EitherPartialOf<String>, String>

    @BeforeAll
    fun beforeAll() {
        deferValidator = object : DeferValidator<IOPartialOf<String>, EitherPartialOf<String>, String>, MonadDefer<IOPartialOf<String>> by IO.monadDefer() {
            override val AE = Either.applicativeError<String>()
        }
    }

    @Test
    fun `IO MonadDefer + Either ApplicativeError`() {
        val product = deferValidator.run {
            tupledN(
                    deferRaiseSomeError(),
                    deferJustString()
            )
        }.fix().attempt().unsafeRunSyncEither()
        product.fold({ assertEquals("error", it) }, {})
    }

    private fun <F, S> DeferValidator<F, S, String>.deferRaiseSomeError() = defer {
        AE.run {
            println("raise some error")
            raiseError<String>("error")
        }.just()
    }

    private fun <F, S> DeferValidator<F, S, String>.deferJustString() = defer {
        AE.run {
            println("This should not be called")
            just("just")
        }.just()
    }
}
