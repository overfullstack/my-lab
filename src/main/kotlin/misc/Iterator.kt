package misc

fun main() {
  recurIterator(listOf(1,2,3).iterator())
}

fun recurIterator(iterator: Iterator<Int>) {
  if (iterator.hasNext()) {
    println(iterator.next())
    recurIterator(iterator)
  }
}
