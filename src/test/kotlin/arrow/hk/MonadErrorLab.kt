/* gakshintala created on 3/16/20 */
package arrow.hk

import arrow.Kind
import arrow.core.Either
import arrow.core.Nel
import arrow.core.NonEmptyList
import arrow.core.Validated
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.either.monadError.monadError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.core.fix
import arrow.core.nel
import arrow.fx.IO
import arrow.fx.IO.Companion.defer
import arrow.fx.extensions.IOApplicative
import arrow.fx.extensions.io.applicativeError.applicativeError
import arrow.fx.extensions.io.monadError.monadError
import arrow.fx.fix
import arrow.typeclasses.ApplicativeError
import arrow.typeclasses.MonadError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonadErrorLab {

    @Test
    fun `IO MonadError with Throwable`() {
        val product = IO.monadError().run {
            tupledN( // This is Lazy and fail-fast
                    defer { raiseSomeErrorIO() },
                    defer { justStringIO() }
            )
        }.fix().unsafeRunSync()
    }

    private fun <S> MonadError<S, Throwable>.raiseSomeErrorIO(): Kind<S, Unit> {
        println("raising an error")
        return raiseError(RuntimeException("error"))
    }

    private fun <S> MonadError<S, Throwable>.justStringIO(): Kind<S, String> {
        println("This won't be printed")
        return just("just")
    }

    @Test
    fun `Either MonadError with String`() {
        val product = Either.monadError<String>().run {
            tupledN( // This is eager, and unlike `IO`, `Either` doesn't fail fast on `raiseError` 
                    raiseSomeError(),
                    justString()
            )
        }.fix()
        product.fold({ assertEquals("error", it) }, {})
    }

    private fun <S> MonadError<S, String>.raiseSomeError(): Kind<S, Unit> {
        println("raising an error")
        return raiseError("error")
    }

    private fun <S> MonadError<S, String>.justString(): Kind<S, String> {
        println("This should not be printed") // But prints
        return just("just")
    }

}
