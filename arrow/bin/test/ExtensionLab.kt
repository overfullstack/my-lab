/* gakshintala created on 7/27/20 */

fun someFun(fn: String.(a: Int) -> Unit) {
  fn("a", 1)
}

fun String.someExt(fn: String.(a: Int) -> Unit) {
  fn(1) // Here String is derived from the same string on which the parent function is called.
}

fun main() {
  someFun { println(length) }
}
