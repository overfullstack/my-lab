package misc

import org.jetbrains.annotations.NotNull

fun someFun(list: List<@NotNull String>) {
    list.map { println(it) }
}
