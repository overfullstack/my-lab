package ga.overfullstack.pojo.composite

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.zacsweers.moshix.adapters.AdaptedBy
import ga.overfullstack.pojo.composite.ConnectGraph.ConnectPQGraphAdapter
import ga.overfullstack.pojo.composite.ConnectGraph.RecordBodyAdapter
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
  fun `Connect Graph JSON --) POJO`() {
    val jsonStr = readFileFromTestResource("composite/connect-graph.json")
    val adapter = Moshi.Builder().build().adapter<ConnectGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `PQ Graph JSON --) POJO`() {
    val jsonStr = readFileFromTestResource("composite/pq-graph.json")
    val adapter = Moshi.Builder().build().adapter<PQGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Connect Graph JSON --) POJO --) PQ Graph JSON`() {
    val jsonStr = readFileFromTestResource("composite/connect-graph.json")
    val connectGraphAdapter =
      Moshi.Builder().add(RecordBodyAdapter).build().adapter<ConnectGraph>()
    val connectGraph = connectGraphAdapter.fromJson(jsonStr)!!
    println(connectGraph)
    val connectGraphToPQJsonAdapter =
      Moshi.Builder()
        .add(RecordBodyAdapter)
        .add(ConnectPQGraphAdapter)
        .build()
        .adapter<ConnectGraph>()
    println(connectGraphToPQJsonAdapter.indent("  ").toJson(connectGraph))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `PQ graph JSON --) Connect Graph POJO --) Connect Graph JSON`() {
    val jsonStr = readFileFromTestResource("composite/pq-graph.json")
    val pqJsonToConnectGraphAdapter =
      Moshi.Builder()
        .add(RecordBodyAdapter)
        .add(ConnectPQGraphAdapter)
        .build()
        .adapter<ConnectGraph>()
    val connectGraph = pqJsonToConnectGraphAdapter.fromJson(jsonStr)!!
    println(connectGraph)
    val connectGraphAdapter =
      Moshi.Builder().add(RecordBodyAdapter).build().adapter<ConnectGraph>()
    println(connectGraphAdapter.indent("  ").toJson(connectGraph))
  }
}
