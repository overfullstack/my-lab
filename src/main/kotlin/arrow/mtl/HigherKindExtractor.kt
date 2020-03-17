/* gakshintala created on 2/15/20 */
package arrow.mtl

import arrow.core.*
import arrow.core.extensions.option.monad.monad
import arrow.core.extensions.traverse
import arrow.typeclasses.Monad

class HigherKindExtractor<F>(M: Monad<F>) : Monad<F> by M {

    private fun getHigherKindBoolean(userName: String) = fx.monad { true }
    private fun booleanResult(user: Option<String>) =
            fx.monad {
                !user.map { getHigherKindBoolean(it) }.getOrElse { false.just() }
            }

    private fun getOptionString() = "".toOption()
    private fun booleanInput(booleanParam: Boolean) = booleanParam
}

class HigherKindExtractor2<F>(M: Monad<F>) : Monad<F> by M {
    private fun getHigherKindBoolean(userName: String) = fx.monad { true }
    private fun booleanInput(booleanParam: Boolean) = booleanParam

    private fun getOptionString() = "".toOption()
    private fun getListOfStrings() = listOf<String>()

    private fun listMapper() = // Using `this` as return type of mapper fn is the same HigherKind as `this`.
            getListOfStrings().k().traverse(this) { str -> getHigherKindBoolean(str).map { booleanInput(it) } }

    private fun optionMapper() = /* this - referring to the monad instance from the class */
            getOptionString().traverse(this) { str -> getHigherKindBoolean(str).map { booleanInput(it) } }
}

fun <F> Monad<F>.getHigherKindBoolean2(user: String) = fx.monad { true }

fun booleanResult2(user: Option<String>) =
        Option.monad().fx.monad {
            val userName = !user
            !getHigherKindBoolean2(userName)
        }.fix().getOrElse { false }
