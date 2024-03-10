package ga.overfullstack.pojo.composite

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.zacsweers.moshix.adapters.AdaptedBy
import ga.overfullstack.pojo.composite.ConnectGraph.ConnectPQGraphAdapter
import ga.overfullstack.pojo.composite.PQPayload.PQPayloadAdapter
import ga.overfullstack.pojo.composite.PQPayload.RecordAdapter
import com.salesforce.revoman.input.readFileInResourcesToString
import org.junit.jupiter.api.Test

class CompositeLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Composite response JSON --) POJO --) JSON`() {
    val compositeRepStr =
      readFileInResourcesToString("composite/resp/composite-query-success-response.json")
    val compositeRepAdapter =
      Moshi.Builder().add(AdaptedBy.Factory()).build().adapter<CompositeQueryResponse>()
    val compositeResp = compositeRepAdapter.fromJson(compositeRepStr)
    println(compositeResp)
    println(compositeRepAdapter.indent("  ").toJson(compositeResp))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Connect graph JSON --) POJO`() {
    val jsonStr = readFileInResourcesToString("composite/req/connect-graph.json")
    val adapter = Moshi.Builder().build().adapter<ConnectGraph>()
    val pojo = adapter.fromJson(jsonStr)!!
    println(pojo)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `PQ payload JSON --) POJO --) Connect graph JSON`() {
    val jsonStr = readFileInResourcesToString("composite/req/pq-payload.json")
    val adapter = Moshi.Builder().add(RecordAdapter).build().adapter<PQPayload>()
    val pqGraph = adapter.fromJson(jsonStr)!!
    println(pqGraph)
    val pqToConnectJsonAdapter =
      Moshi.Builder().add(RecordAdapter).add(PQPayloadAdapter).build().adapter<PQPayload>()
    println(pqToConnectJsonAdapter.indent("  ").toJson(pqGraph))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `toJson ~ Connect graph JSON --) Connect graph POJO --) PQ payload JSON`() {
    val jsonStr = readFileInResourcesToString("composite/req/connect-graph.json")
    val connectGraphAdapter = Moshi.Builder().build().adapter<ConnectGraph>()
    val connectGraph = connectGraphAdapter.fromJson(jsonStr)!!
    println(connectGraph)
    val connectGraphToPQJsonAdapter =
      Moshi.Builder().add(ConnectPQGraphAdapter).build().adapter<ConnectGraph>()
    println(connectGraphToPQJsonAdapter.indent("  ").toJson(connectGraph))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `fromJson ~ PQ payload JSON --) Connect graph POJO --) Connect graph JSON`() {
    val jsonStr = readFileInResourcesToString("composite/req/pq-payload.json")
    val pqJsonToConnectGraphAdapter =
      Moshi.Builder().add(ConnectPQGraphAdapter).build().adapter<ConnectGraph>()
    val connectGraph = pqJsonToConnectGraphAdapter.fromJson(jsonStr)!!
    println(connectGraph)
    val connectGraphAdapter = Moshi.Builder().build().adapter<ConnectGraph>()
    println(connectGraphAdapter.indent("  ").toJson(connectGraph))
  }
}
