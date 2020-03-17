package arrow.hk

import arrow.Kind
import arrow.core.Either
import arrow.core.extensions.either.monadError.monadError
import arrow.core.fix
import arrow.typeclasses.MonadError

object Nuke
object Target
object Impacted

sealed class NukeException {
    object SystemOffline : NukeException()
    object RotationNeedsOil : NukeException()
    data class MissedByMeters(val meters: Int) : NukeException()
}

typealias SystemOffline = NukeException.SystemOffline
typealias RotationNeedsOil = NukeException.RotationNeedsOil
typealias MissedByMeters = NukeException.MissedByMeters

fun <F> MonadError<F, NukeException>.arm(): Kind<F, Nuke> = just(Nuke)
fun <F> MonadError<F, NukeException>.aim(): Kind<F, Target> = just(Target)
fun <F> MonadError<F, NukeException>.launch(target: Target, nuke: Nuke):
        Kind<F, Impacted> = raiseError(MissedByMeters(5))

fun <F> MonadError<F, NukeException>.attack(): Kind<F, Impacted> =
        fx.monad {
            val nuke = !arm<F>()
            val target = !aim<F>()
            val impact = !launch<F>(target, nuke)
            impact
        }

fun main() {
    val result = Either.monadError<NukeException>().attack()
    println(result.fix())
}
