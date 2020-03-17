package arrow.typeclasses.diclash

import arrow.core.ListK
import arrow.core.Option
import arrow.core.extensions.listk.traverse.traverse
import arrow.core.extensions.option.monad.monad

interface ParserDependencies {
    fun MO() = Option.monad()
    fun TL() = ListK.traverse()

    companion object : ParserDependencies
}
