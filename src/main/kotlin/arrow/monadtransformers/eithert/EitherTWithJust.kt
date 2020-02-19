/* gakshintala created on 2/17/20 */
package arrow.monadtransformers.eithert

import arrow.core.left
import arrow.core.right
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.monad.monad
import arrow.fx.fix
import arrow.mtl.EitherT
import arrow.mtl.extensions.eithert.monad.monad
import arrow.mtl.value
import arrow.typeclasses.Monad


fun <F> Monad<F>.eitherTWithJustGeneric() = EitherT.monad<F, String>(this).fx.monad {
    val x = !EitherT(just("".right())).value()
    val y = EitherT(just("".right()))
}

fun eitherWithJustIO() = EitherT.monad<ForIO, String>(IO.monad()).fx.monad { 
    val x = !EitherT(IO.just(
            if (sendFalse()) "rightX".right() else "leftX".left()
    ))
    val y = !EitherT(IO.just("rightY".right()))
    y
}.value().fix()

fun sendFalse() = false

fun main() {
    println(eitherWithJustIO().unsafeRunSync())
}
