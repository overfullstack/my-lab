package ga.overfullstack.pojo.composite

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import org.http4k.format.list
import org.http4k.format.obj
import org.http4k.format.string

@JsonClass(generateAdapter = true)
data class ConnectGraph(
  val graph: Graph,
  val isSetGraph: Boolean,
  val isSetPricingPref: Boolean,
  val pricingPref: String
) {
  @JsonClass(generateAdapter = true)
  data class Graph(
    val graphId: String,
    val isSetGraphId: Boolean,
    val isSetRecords: Boolean,
    val records: Records
  ) {
    @JsonClass(generateAdapter = true)
    data class Records(val isSetRecordsList: Boolean, val recordsList: List<Records>) {
      @JsonClass(generateAdapter = true)
      data class Records(
        val isSetRecord: Boolean,
        val isSetReferenceId: Boolean,
        val record: Record,
        val referenceId: String
      ) {
        @JsonClass(generateAdapter = true)
        data class Record(val isSetRecordBody: Boolean, val recordBody: RecordBody) {
          @JsonClass(generateAdapter = true)
          data class RecordBody(val attributes: Attributes, val recordBody: Map<String, String>) {
            @JsonClass(generateAdapter = true)
            data class Attributes(val method: String, val type: String)
          }
        }
      }
    }
  }
  
  class ConnectGraphToPQJsonAdapter : JsonAdapter<ConnectGraph>() {

    override fun fromJson(reader: JsonReader): ConnectGraph? {
      TODO("Not yet implemented")
    }

    @ToJson
    override fun toJson(writer: JsonWriter, connectGraph: ConnectGraph?) {
      with(writer) {
        obj(connectGraph) {
          string("pricingPref", pricingPref)
          obj("graph", graph) {
            string("graphId", graphId)
            list("records", records.recordsList) {
              obj(this) {
                string("referenceId", referenceId)
                obj("record", record) {
                  obj("attributes", record.recordBody.attributes) {
                    string("type", type)
                    string("method", method)
                  }
                  obj(recordBody.recordBody) { entries.forEach { string(it.key, it.value) } }
                }
              }
            }
          }
        }
      }
    }
  }
}
