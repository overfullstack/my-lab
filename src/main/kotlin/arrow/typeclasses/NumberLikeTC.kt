/* gakshintala created on 2/16/20 */
package arrow.typeclasses

interface NumberLikeTC<NumberT> {
    fun add(a: NumberT, b: NumberT): NumberT
}

// To make Double a member of NumberLikeTC.
val numberLikeDouble = object : NumberLikeTC<Double> {
    override fun add(a: Double, b: Double) = a + b
}

// To make Int a member of NumberLikeTC.
val numberLikeInt = object : NumberLikeTC<Int> {
    override fun add(a: Int, b: Int) = a + b
}

// NumberT type should be a member of NumberLikeTC type class by implementing all its methods.
fun <NumberT> addAllNums(numbers: List<NumberT>, NTC: NumberLikeTC<NumberT>) =
        numbers.reduce { acc, i -> NTC.add(acc, i) }

fun main() {
    println(addAllNums(listOf(1, 2, 3), numberLikeInt))
    println(addAllNums(listOf(1.0, 2.0, 3.0), numberLikeDouble))
}
