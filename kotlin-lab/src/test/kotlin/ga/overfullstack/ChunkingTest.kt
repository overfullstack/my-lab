package ga.overfullstack

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ChunkingTest :
  StringSpec({
    "Chunking Test" {
      val ctx3 =
        mutableMapOf(
          Id(1) to ContextRecord(4, 1, null),
          Id(2) to ContextRecord(2, 1, null),
          Id(3) to ContextRecord(2, 1, null),
          Id(4) to ContextRecord(7, 2, null),
          Id(5) to ContextRecord(10, 3, null),
          Id(6) to ContextRecord(3, 4, null),
          Id(7) to ContextRecord(2, 5, null),
          Id(8) to ContextRecord(1, 5, null),
          Id(9) to ContextRecord(1, 5, null),
          Id(10) to ContextRecord(1, 5, null),
        )
      forAll(row(ctx3, 3)) { context, result -> greedyChunking(context) shouldBe result }
    }
  })

internal fun List<Int>.toContext(): Context =
  withIndex()
    .associate { Id(it.index) to ContextRecord(it.value, indices.random(), null) }
    .toMutableMap()
