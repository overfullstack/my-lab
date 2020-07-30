/* gakshintala created on 2/19/20 */
package arrow.io

import arrow.core.Either
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.flattenEither
import arrow.fx.handleError
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class IOLab {

    private fun getIO() = IO {
        println(Thread.currentThread().name)
    }

    @Test
    fun `IO suspended`() = runBlocking { getIO().suspended() }

    @Test
    fun `IO unsafe`() {
        getIO().unsafeRunSync()
    }

    @Test
    fun `IO attempt`() {
        val unsafeRunSync = getIO().attempt().unsafeRunSync()
        val attempt: IO<Unit> = getIO().attempt().flattenEither() // This doesn't execute anything.
        val unsafeRunSyncEither1 = attempt.unsafeRunSync() // this is same as flatten
    }

    private fun trueForEver() = true

    @Test
    fun `IO with Exception`() {
        val io = IO { if (trueForEver()){
            println("error")
            throw RuntimeException("error")
        } else 1 }
        // attempt and flattenEither produce deferred computations. It's like returning a function 
        val attempt: IO<Either<Throwable, Int>> = io.attempt() // this doesn't execute anything
        val flattenEither: IO<Int> = attempt.flattenEither() // this doesn't execute anything 
        //val result: Int = flattenEither.unsafeRunSync() // This executes and throws a RuntimeException.
    }

    @Test
    fun `IO with handle error`() {
        val io = IO { if (trueForEver()){
            println("error")
            throw RuntimeException("error")
        } else 1 }
        val io2 = if (trueForEver()) IO.raiseError(RuntimeException("error")) else IO.just(1)
        val handleError = io.handleError { it.message }
        println(handleError.unsafeRunSync())
    }

    @Test
    fun `Is bind lazy`() {
        println(binderTest()) // Yes lazy, only executes with unsafeRunSync
    }

    fun binderTest(): IO<String> = IO.fx {
        val bind = !effect { "abc" }
        println(bind)
        bind
    }

    private fun someBooleanResult(i: Int) = false
}
