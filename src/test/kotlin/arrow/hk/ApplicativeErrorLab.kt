/* gakshintala created on 3/16/20 */
package arrow.hk

import arrow.Kind
import arrow.core.Either
import arrow.core.Nel
import arrow.core.NonEmptyList
import arrow.core.Option
import arrow.core.Validated
import arrow.core.extensions.EitherApplicativeError
import arrow.core.extensions.ValidatedApplicativeError
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.fx
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.option.applicativeError.applicativeError
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.core.fix
import arrow.core.nel
import arrow.fx.IO
import arrow.fx.IO.Companion.defer
import arrow.fx.extensions.io.applicativeError.applicativeError
import arrow.fx.fix
import arrow.typeclasses.ApplicativeError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ApplicativeErrorLab {

    @Test
    fun `IO ApplicativeError with Throwable, FailFast`() {
        val product = IO.applicativeError().run {
            val raiseSomeErrorIO = raiseSomeErrorIO()
            val justStringIO = justStringIO()
            tupledN( // ✅ Fail-fast ❌ Lazy
                    raiseSomeErrorIO,
                    justStringIO
            )
        }.fix().unsafeRunSync()
    }

    @Test
    fun `IO ApplicativeError with Throwable, Lazy + FailFast`() {
        val product = IO.applicativeError().run {
            val deferRaiseSomeErrorIO = defer { raiseSomeErrorIO() }
            val deferJustStringIO = defer { justStringIO() }
            tupledN( // ✅ Fail-fast ✅ Lazy
                    deferRaiseSomeErrorIO,
                    deferJustStringIO
            )
        }.fix().unsafeRunSync()
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
    fun `Option ApplicativeError with String`() {
        val product = Option.applicativeError().run {
            tupledN(
                    raiseSomeErrorUnit(),
                    justStringUnit()
            )
        }.fix()
        assertTrue(product.isEmpty())
        assertTrue(Option.applicativeError().raiseError<Unit>(Unit).isEmpty())
    }

    private fun <S> ApplicativeError<S, Unit>.raiseSomeErrorUnit(): Kind<S, Unit> {
        println("raising an error")
        return raiseError(Unit)
    }

    private fun <S> ApplicativeError<S, Unit>.justStringUnit(): Kind<S, String> {
        println("This should not be printed") // But prints
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
        assertTrue(Either.applicativeError<String>().raiseError<Unit>("Dark").isLeft())
    }

    private fun <S> ApplicativeError<S, String>.raiseSomeError(): Kind<S, Unit> {
        println("raising an error")
        return raiseError("error")
    }

    private fun <S> ApplicativeError<S, String>.justString(): Kind<S, String> {
        println("This should not be printed") // But prints
        return just("just")
    }


    @Test
    fun `Validated ApplicativeError with Nel{String}`() {
        val errorAccumulation = Validated.applicativeError(NonEmptyList.semigroup<String>())
        val product = errorAccumulation.run {
            // `tupledN` internally uses `product` which has an `ap` method which does combining of errors for validated using a Semi-Group.
            orchestrate()
        }
        product.fold({ assertEquals(NonEmptyList("error", "error"), it) }, {})
    }

    @Test
    fun `Either ApplicativeError with Nel{String}`() { // ✅ Fail-fast ✅ Lazy
        val failFast = Either.applicativeError<Nel<String>>()
        val product = failFast.run {
            orchestrate()
        }
        product.fold({ assertEquals("error".nel(), it) }, {})
    }

    private fun EitherApplicativeError<Nel<String>>.orchestrate() = Either.fx<Nel<String>, String> {
        !raiseSomeErrorNel()
        !justStringNel()
    }

    private fun ValidatedApplicativeError<Nel<String>>.orchestrate() =
            tupledN(
                    raiseSomeErrorNel(),
                    raiseSomeErrorNel(),
                    justStringNel()
            ).fix()

    private fun <S> ApplicativeError<S, Nel<String>>.raiseSomeErrorNel(): Kind<S, Unit> {
        println("raising an error")
        return raiseError("error".nel())
    }

    private fun <S> ApplicativeError<S, Nel<String>>.justStringNel(): Kind<S, String> {
        println("This should not be printed")
        return just("just")
    }

}
