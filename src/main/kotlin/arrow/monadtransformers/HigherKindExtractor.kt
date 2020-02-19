/* gakshintala created on 2/15/20 */
package arrow.monadtransformers

import arrow.core.*
import arrow.core.extensions.option.monad.monad
import arrow.core.extensions.traverse
import arrow.typeclasses.Monad

class HigherKindExtractor<F>(M: Monad<F>) : Monad<F> by M {

    private fun getIsSomethingTrueInHigherKind(userName: String) = fx.monad { true }
    private fun booleanResult(user: Option<String>) =
            fx.monad {
                !user.map { getIsSomethingTrueInHigherKind(it) }.getOrElse { false.just() }
            }

    private fun getOptionString() = "".toOption()
    private fun booleanInput(booleanParam: Boolean) = booleanParam
}

class HigherKindExtractor2<F>(M: Monad<F>) : Monad<F> by M {
    private fun getIsSomethingTrueInHigherKind(userName: String) = fx.monad { true }
    private fun booleanInput(booleanParam: Boolean) = booleanParam

    private fun getOptionString() = "".toOption()
    private fun getListOfStrings() = listOf<String>()

    private fun listMapper() = fx.monad {
        getListOfStrings().k().traverse(this) { getIsSomethingTrueInHigherKind(it).map { booleanInput(it) } }
    }

    private fun optionMapper() = /* this - referring to the monad instance from the class */
            getOptionString().traverse(this) { str -> getIsSomethingTrueInHigherKind(str).map { booleanInput(it) } }
}

fun <F> Monad<F>.getIsSomethingTrueInHigherKind2(user: String) = fx.monad { true }

fun booleanResult2(user: Option<String>) =
        Option.monad().fx.monad {
            val userName = !user
            !getIsSomethingTrueInHigherKind2(userName)
        }.fix().getOrElse { false }
