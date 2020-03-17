package arrow.hk

import arrow.Kind
import arrow.core.Either
import arrow.core.extensions.either.monadError.monadError
import arrow.core.fix
import arrow.typeclasses.MonadError

object Lettuce
object Knife
object Salad

sealed class CookingException : Throwable() {
    object LettuceIsRotten : CookingException()
    object KnifeNeedsSharpening : CookingException()
    data class InsufficientAmount(val quantityInGrams: Int) : CookingException()
}

typealias NastyLettuce = CookingException.LettuceIsRotten
typealias KnifeIsDull = CookingException.KnifeNeedsSharpening
typealias InsufficientAmountOfLettuce = CookingException.InsufficientAmount


fun <F> MonadError<F, CookingException>.takeFoodFromRefrigerator(): Kind<F, Lettuce> = just(Lettuce)
fun <F> MonadError<F, CookingException>.getKnife(): Kind<F, Knife> = just(Knife)
fun <F> MonadError<F, CookingException>.lunch(knife: Knife, food: Lettuce):
        Kind<F, Salad> = raiseError(InsufficientAmountOfLettuce(5))

fun <F> MonadError<F, CookingException>.prepare(): Kind<F, Salad> =
        fx.monad {
            val lettuce = takeFoodFromRefrigerator().bind()
            val knife = getKnife().bind()
            val salad = lunch(knife, lettuce).bind()
            salad
        }

fun <F> MonadError<F, CookingException>.prepare1(): Kind<F, Salad> =
        tupledN(getKnife(), takeFoodFromRefrigerator()).flatMap({ (nuke, target) -> lunch<F>(nuke, target) })

/*fun <F> MonadError<F, CookingException>.lunchImpure(target: Knife, nuke: Lettuce): Salad {
    throw InsufficientAmountOfLettuce(5)
}

fun <F> MonadError<F, CookingException>.prepare2(): Kind<F, Salad> =
        fx.monadThrow {
            val lettuce = takeFoodFromRefrigerator<F>().bind()
            val knife = getKnife<F>().bind()
            val salad = lunchImpure<F>(knife, lettuce).bind()
            salad
        }*/

fun main() {
    val result = Either.monadError<NukeException>().attack()
    println(result.fix())
}
