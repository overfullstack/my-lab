package ga.overfullstack.pojo.composite


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompositeGraph(
  val pricingPref: String,
  val graph: Graph,
) {
  @JsonClass(generateAdapter = true)
  data class Graph(
    val graphId: String,
    val isSetGraphId: Boolean,
    val isSetRecords: Boolean,
    val records: Records
  ) {
    @JsonClass(generateAdapter = true)
    data class Records(
      val isSetRecordsList: Boolean,
      val recordsList: List<Records>
    ) {
      @JsonClass(generateAdapter = true)
      data class Records(
        val isSetRecord: Boolean,
        val isSetReferenceId: Boolean,
        val record: Record,
        val referenceId: String
      ) {
        @JsonClass(generateAdapter = true)
        data class Record(
          val isSetRecordBody: Boolean,
          val recordBody: RecordBody
        ) {
          @JsonClass(generateAdapter = true)
          data class RecordBody(
            @Json(name = "Name")
            val name: String,
            val quoteId: String,
            @Json(name = "Status")
            val status: String
          )
        }
      }
    }
  }
}
