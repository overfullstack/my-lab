package arrow.typeclasses.diclash

import arrow.core.Option
import arrow.core.fix
import arrow.core.k
import arrow.core.none

class Parser : ParserDependencies by ParserDependencies {
    fun parseInt(s: String): Option<Int> = MO().run {
        val kind = try {
            s.toInt().just()
        } catch (t: Throwable) {
            none<Int>()
        }
        kind.fix()
    }

    fun parseInts(l: List<Option<String>>) = TL().run {
        l.k().traverse(MO()) {
            MO().run {
                it.flatMap { parseInt(it) }
            }
        }.fix()
    }

}
