package ga.overfullstack.pojo.composite

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.zacsweers.moshix.adapters.AdaptedBy
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

class CompositeLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Unmarshall composite response json to pojo`() {
    val compositeRepStr = readFileFromTestResource("composite/composite-response.json")
    val compositeRepAdapter = Moshi.Builder()
      .add(AdaptedBy.Factory())
      .build().adapter<CompositeResponse>()
    val compositeResp = compositeRepAdapter.fromJson(compositeRepStr)
    println(compositeResp)
    println(compositeRepAdapter.indent("  ").toJson(compositeResp))
  }
  
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Unmarshall Json to composite Graph`() {
    val jsonStr = readFileFromTestResource("composite/connect-graph.json")
    val adapter = Moshi.Builder().build().adapter<ConnectGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Unmarshall json to composite Graph PQ`() {
    val jsonStr = readFileFromTestResource("composite/pq-graph.json")
    val adapter = Moshi.Builder().build().adapter<PQGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Unmarshall composite Graph PQ JSON to Composite Graph Connect POJO`() {
    val jsonStr = readFileFromTestResource("composite/connect-graph.json")
    val adapter = Moshi.Builder().add(AdaptedBy.Factory()).build().adapter<ConnectGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }
}
