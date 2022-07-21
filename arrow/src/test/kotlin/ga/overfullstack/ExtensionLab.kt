package ga.overfullstack

/* gakshintala created on 7/27/20 */

fun someFun(fn: String.(a: Int) -> Unit) {
  fn("abc", 1) // Instance needs to be passed explicitly
}

fun String.someExt(fn: String.(a: Int) -> Unit) {
  fn(2) // `fn` in extension takes the same instance used to call `someExt` function
}

fun main() {
  someFun { println("$it.$this: $length") }
  "abcd".someExt { println("$it.$this: $length") }
}
