/* gakshintala created on 2/16/20 */
package arrow.typeclasses

interface AdderTC<AddableT> {
    fun add(a: AddableT, b: AddableT): AddableT

    // AddableT type should be a member of AdderTC type class by implementing all its methods.
    fun addAll(addables: List<AddableT>) =
            addables.reduce { acc, i -> add(acc, i) }
}

// To make Int a member of AdderTC.
val intCombine = object : AdderTC<Int> {
    override fun add(a: Int, b: Int) = a + b
}

// To make String a member of AdderTC.
val stringCombine = object : AdderTC<String> {
    override fun add(a: String, b: String) = a + b
}

fun main() {
    println(intCombine.addAll(listOf(1, 2, 3)))
    println(stringCombine.addAll(listOf("a", "b", "c")))
}
