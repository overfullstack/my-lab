/* gakshintala created on 3/4/20 */
package arrow.typeclasses

data class Apple(val weight: Int)

val appleComparator = Comparator<Apple> { apple1, apple2 ->
    apple1?.let { first ->
        apple2?.let { second ->
            first.weight.compareTo(second.weight)
        } ?: 1
    } ?: 0
}

fun main() {
    println(listOf(Apple(3), Apple(1), Apple(2)).sortedWith(appleComparator))
}
