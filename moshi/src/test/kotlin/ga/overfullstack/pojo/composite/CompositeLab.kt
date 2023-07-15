package ga.overfullstack.pojo.composite

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.zacsweers.moshix.adapters.AdaptedBy
import ga.overfullstack.pojo.composite.ConnectGraph.ConnectPQGraphAdapter
import ga.overfullstack.pojo.composite.PQGraph.PQConnectGraphAdapter
import ga.overfullstack.pojo.composite.PQGraph.RecordAdapter
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

class CompositeLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Composite response JSON --) POJO --) JSON`() {
    val compositeRepStr = readFileFromTestResource("composite/composite-response.json")
    val compositeRepAdapter =
      Moshi.Builder().add(AdaptedBy.Factory()).build().adapter<CompositeResponse>()
    val compositeResp = compositeRepAdapter.fromJson(compositeRepStr)
    println(compositeResp)
    println(compositeRepAdapter.indent("  ").toJson(compositeResp))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Connect graph JSON --) POJO`() {
    val jsonStr = readFileFromTestResource("composite/connect-graph.json")
    val adapter = Moshi.Builder().build().adapter<ConnectGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `PQ graph JSON --) POJO --) Connect graph JSON`() {
    val jsonStr = readFileFromTestResource("composite/pq-payload.json")
    val adapter = Moshi.Builder().add(RecordAdapter).build().adapter<PQGraph>()
    val pqGraph = adapter.fromJson(jsonStr)!!
    println(pqGraph)
    val pqToConnectJsonAdapter =
      Moshi.Builder().add(RecordAdapter).add(PQConnectGraphAdapter).build().adapter<PQGraph>()
    println(pqToConnectJsonAdapter.indent("  ").toJson(pqGraph))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `toJson ~ Connect graph JSON --) Connect graph POJO --) PQ graph JSON`() {
    val jsonStr = readFileFromTestResource("composite/connect-graph.json")
    val connectGraphAdapter = Moshi.Builder().build().adapter<ConnectGraph>()
    val connectGraph = connectGraphAdapter.fromJson(jsonStr)!!
    println(connectGraph)
    val connectGraphToPQJsonAdapter =
      Moshi.Builder().add(ConnectPQGraphAdapter).build().adapter<ConnectGraph>()
    println(connectGraphToPQJsonAdapter.indent("  ").toJson(connectGraph))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `fromJson ~ PQ graph JSON --) Connect graph POJO --) Connect graph JSON`() {
    val jsonStr = readFileFromTestResource("composite/pq-payload.json")
    val pqJsonToConnectGraphAdapter =
      Moshi.Builder().add(ConnectPQGraphAdapter).build().adapter<ConnectGraph>()
    val connectGraph = pqJsonToConnectGraphAdapter.fromJson(jsonStr)!!
    println(connectGraph)
    val connectGraphAdapter = Moshi.Builder().build().adapter<ConnectGraph>()
    println(connectGraphAdapter.indent("  ").toJson(connectGraph))
  }
}
