package ga.overfullstack

private const val MAX_CHUNK_WEIGHT_PER_JOB_ITEM = 15 // TBD after perf-testing
private const val QUERY_GROUP_COUNT = 2 // TBD after perf-testing

data class ContextRecord(val weight: Int, val groupNumber: Int, val chunkNumber: Int?)

@JvmInline value class Id(val id: Int)

typealias Context = MutableMap<Id, ContextRecord>

fun greedyChunking(ctx: Context): Int {
  var chunkIndex = 0
  var chunkWeight = 0
  val idsInChunk = mutableListOf<Id>()
  var queryStartGroupNumber = 0
  while (true) { // Coz you don't know when the query drys-up
    val ctxGroups = ctx.queryNext(queryStartGroupNumber..queryStartGroupNumber + QUERY_GROUP_COUNT)
    // Query dried-up, all records stamped
    if (ctxGroups.isEmpty()) {
      // Stamp the final Chunk
      if (idsInChunk.isNotEmpty()) {
        chunkIndex++
        ctx.stampChunkNumber(idsInChunk, chunkIndex)
      }
      return chunkIndex
    }

    for (ctxGroup in ctxGroups) {
      val groupWeight = ctxGroup.values.sumOf { it.weight }
      chunkWeight += groupWeight

      // We allow more than `MAX_CHUNK_WEIGHT_PER_JOB_ITEM` to accommodate large Bundles
      if (chunkWeight > MAX_CHUNK_WEIGHT_PER_JOB_ITEM) {
        // Full! Stamp the current Chunk
        if (idsInChunk.isNotEmpty()) {
          chunkIndex++
          ctx.stampChunkNumber(idsInChunk, chunkIndex)
          idsInChunk.clear()
        }
        // And start a New Chunk
        chunkWeight = groupWeight
      }
      idsInChunk.addAll(ctxGroup.keys)
    }
    // Stamping with a temp chunkNumber to not qualify for `queryNext()` in the next iteration
    ctx.stampChunkNumber(idsInChunk, -1)
    queryStartGroupNumber += QUERY_GROUP_COUNT
  }
}

private fun Context.queryNext(groupRange: IntRange): List<Map<Id, ContextRecord>> =
    entries
        .asSequence()
        .filter { (_, ctxRec) -> ctxRec.chunkNumber == null && ctxRec.groupNumber in groupRange }
        .groupBy { (_, ctxRec) -> ctxRec.groupNumber }
        .values
        .map { it.associate { it.toPair() } }
        .sortedByDescending { it.values.sumOf { it.weight } }

private fun Context.stampChunkNumber(ids: List<Id>, chunkNumber: Int) =
    ids.forEach {
      computeIfPresent(it) { _, contextRecord -> contextRecord.copy(chunkNumber = chunkNumber) }
    }

fun main() {
  val ctx =
      mutableMapOf(
          Id(1) to ContextRecord(4, 1, null),
          Id(2) to ContextRecord(2, 1, null),
          Id(3) to ContextRecord(2, 1, null),
          Id(4) to ContextRecord(8, 2, null),
          Id(5) to ContextRecord(20, 3, null),
          Id(6) to ContextRecord(3, 4, null),
          Id(7) to ContextRecord(2, 5, null),
          Id(8) to ContextRecord(1, 5, null),
          Id(9) to ContextRecord(1, 5, null),
          Id(10) to ContextRecord(1, 5, null))
  val noOfChunks = greedyChunking(ctx)
  println(noOfChunks) // 3
  ctx.entries.groupBy({ it.value.chunkNumber }, { it.key to it.value.weight }).entries.map {  it.key to it.value to it.value.sumOf { it.second }  }.forEach(::println)
}
