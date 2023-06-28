package ga.overfullstack.composite

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.composite.CompositeGraph
import ga.overfullstack.pojo.composite.CompositeGraphPQ
import ga.overfullstack.pojo.composite.CompositeResponse
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

class CompositeLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read Json to composite Graph`() {
    val jsonStr = readFileFromTestResource("composite/composite-graph-with-recordList.json")
    val adapter = Moshi.Builder().build().adapter<CompositeGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read json to composite Graph PQ`() {
    val jsonStr = readFileFromTestResource("composite/composite-graph-pq.json")
    val adapter = Moshi.Builder().add(CompositeGraphPQ.RecordAdapterFactory()).build().adapter<CompositeGraphPQ>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read composite response to list map`() {
    val collectionStr = readFileFromTestResource("composite/composite-response.json")
    val mapAdapter = Moshi.Builder().build().adapter<Any?>()
    val compositeResp = mapAdapter.fromJson(collectionStr)
    println(compositeResp)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read composite response json to pojo`() {
    val compositeRepStr = readFileFromTestResource("composite/composite-response.json")
    val compositeRepAdapter = Moshi.Builder()
      .add(CompositeResponse.RecordAdapterFactory())
      .build().adapter<CompositeResponse>()
    val compositeResp = compositeRepAdapter.fromJson(compositeRepStr)
    println(compositeResp)
    println(compositeRepAdapter.indent("  ").toJson(compositeResp))
  }
}
