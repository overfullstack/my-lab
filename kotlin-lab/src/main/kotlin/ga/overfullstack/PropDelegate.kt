package ga.overfullstack

class PropDelegate(map: MutableMap<String, String>) {
  var prop: String by map
}

fun main() {
  val map = mutableMapOf<String, String>()
  val propDelegate = PropDelegate(map)
  println(map)
}
