/* gakshintala created on 3/4/20 */
package arrow.typeclasses

data class Apple(val weight: Int)

val apple = Comparator<Apple> { o1, o2 ->
    o1?.let { first ->
        o2?.let { second ->
            first.weight.compareTo(second.weight)
        } ?: 1
    } ?: 0
}
