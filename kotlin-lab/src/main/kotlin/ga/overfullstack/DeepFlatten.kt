package ga.overfullstack

fun deepFlattenList(items: List<*>): List<*> =
  items
    .asSequence()
    .flatMap {
      when (it) {
        is List<*> -> deepFlattenList(it)
        else -> listOf(it)
      }
    }
    .toList()
