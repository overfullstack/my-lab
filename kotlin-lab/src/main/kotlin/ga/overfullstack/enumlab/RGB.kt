package ga.overfullstack.enumlab

enum class RGB {
  RED,
  GREEN,
  BLUE
}

inline fun <reified T : Enum<T>> printAllValues() {
  print(enumValues<T>().joinToString { it.name })
}
