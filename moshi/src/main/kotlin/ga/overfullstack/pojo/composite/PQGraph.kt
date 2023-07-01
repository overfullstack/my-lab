package ga.overfullstack.pojo.composite

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PQGraph(val graph: Graph, val pricingPref: String) {
  @JsonClass(generateAdapter = true)
  data class Graph(val graphId: String, val records: List<Record>) {
    @JsonClass(generateAdapter = true)
    data class Record(val record: Record, val referenceId: String) {
      @JsonClass(generateAdapter = true)
      data class Record(val attributes: Attributes, val recordBody: Map<String, String>) {
        @JsonClass(generateAdapter = true)
        data class Attributes(val type: String, val method: String)
      }
    }
  }
}
