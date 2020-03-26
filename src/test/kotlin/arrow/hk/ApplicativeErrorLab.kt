/* gakshintala created on 3/16/20 */
package arrow.hk

import arrow.Kind
import arrow.core.Either
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.fix
import arrow.fx.IO
import arrow.fx.IO.Companion.defer
import arrow.fx.IOPartialOf
import arrow.fx.extensions.IOApplicative
import arrow.fx.extensions.io.applicativeError.applicativeError
import arrow.fx.fix
import arrow.fx.flatMapLeft
import arrow.typeclasses.ApplicativeError
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ApplicativeErrorLab {

    @Test
    fun `IO ApplicativeError with Throwable`() {
        val product = IO.applicativeError<Throwable>().run {
            tupledN(
                    defer { raiseSomeErrorIO() },
                    defer { justStringIO() }
            )
        }.fix().attempt().unsafeRunSyncEither()
        product.fold({ assertEquals("error", it.message) }, {})
    }

    private fun <S> ApplicativeError<S, Throwable>.raiseSomeErrorIO(): Kind<S, Unit> {
        println("raising an error")
        return raiseError(RuntimeException("error"))
    }

    private fun <S> ApplicativeError<S, Throwable>.justStringIO(): Kind<S, String> {
        println("This won't be printed")
        return just("just")
    }

    @Test
    fun `Either ApplicativeError with String`() {
        val product = Either.applicativeError<String>().run {
            tupledN( // This is eager, and unlike `IO`, `Either` doesn't fail fast on `raiseError` 
                    raiseSomeError(),
                    justString()
            )
        }.fix()
        product.fold({ assertEquals("error", it) }, {})
    }

    private fun <S> ApplicativeError<S, String>.raiseSomeError(): Kind<S, Unit> {
        println("raising an error")
        return raiseError("error")
    }

    private fun <S> ApplicativeError<S, String>.justString(): Kind<S, String> {
        println("This should not be printed")
        return just("just")
    }
    
    fun <E> IO.Companion.error(): ApplicativeError<IOPartialOf<E>, E> =
            object : ApplicativeError<IOPartialOf<E>, E>, IOApplicative<E> {
                override fun <A> raiseError(e: E): Kind<IOPartialOf<E>, A> =
                        IO.raiseError(e)

                override fun <A> Kind<IOPartialOf<E>, A>.handleErrorWith(f: (E) -> Kind<IOPartialOf<E>, A>): Kind<IOPartialOf<E>, A> =
                        fix().flatMapLeft(f)
            }

    @Test
    fun `IO ApplicativeError with String`() {
        val product = IO.error<String>().run {
            tupledN(
                    defer { raiseSomeError() },
                    defer { justString() }
            )
        }.fix().unsafeRunSyncEither()
        product.fold({ assertEquals("error", it) }, {})
    }

}
