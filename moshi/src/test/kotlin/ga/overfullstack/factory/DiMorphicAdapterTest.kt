package ga.overfullstack.factory

import com.google.common.truth.Truth.assertThat
import com.salesforce.revoman.input.readFileInResourcesToString
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.composite.GraphResponse
import ga.overfullstack.pojo.composite.GraphResponse.Graph
import ga.overfullstack.pojo.composite.GraphResponse.Graph.ErrorGraph
import ga.overfullstack.pojo.composite.GraphResponse.Graph.SuccessGraph
import org.junit.jupiter.api.Test

class DiMorphicAdapterTest {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Graph Success --) POJO`() {
    val graphFactory =
      DiMorphicAdapter.of(
        Graph::class.java,
        "isSuccessful",
        true,
        SuccessGraph::class.java,
        ErrorGraph::class.java
      )
    val graphResponseAdapter = Moshi.Builder().add(graphFactory).build().adapter<GraphResponse>()
    val successGraphResponse =
      graphResponseAdapter.fromJson(
        readFileInResourcesToString("composite/graph/resp/graph-success-response.json")
      )
    assertThat(successGraphResponse?.graphs?.get(0)).isInstanceOf(SuccessGraph::class.java)
    val errorGraphResponse =
      graphResponseAdapter.fromJson(
        readFileInResourcesToString("composite/graph/resp/graph-error-response.json")
      )
    assertThat(errorGraphResponse?.graphs?.get(0)).isInstanceOf(ErrorGraph::class.java)
  }
}
