@file:JvmName("Memoize")

package ga.overfullstack

import arrow.core.memoize


private val idToEntityObj: MutableMap<Any, String> = mutableMapOf()

@JvmField
val entityObjIdRandomGenerator: (Any) -> String = { id: Any ->
  idToEntityObj.computeIfAbsent(id) { "1$id" }
}.memoize()

