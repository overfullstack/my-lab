package ga.overfullstack

import arrow.core.memoize
import com.google.common.collect.HashBiMap
import org.apache.commons.lang3.RandomStringUtils.random
import org.junit.jupiter.api.Test

private val cache: HashBiMap<Any, Any> = HashBiMap.create()
private val anyRandomGenerator: (Any) -> Any = { id: Any ->
  cache.computeIfAbsent(id) { random(18, true, true) }
}.memoize()

class MemoizedLab {
  @Test
  fun `memoized random generator`() {
    anyRandomGenerator(Pair(1, 2))
    anyRandomGenerator(1)
    println(anyRandomGenerator(1))
    println(anyRandomGenerator(Pair(1, 2)))
  }
}
