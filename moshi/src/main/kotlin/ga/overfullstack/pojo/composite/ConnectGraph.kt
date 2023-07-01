package ga.overfullstack.pojo.composite

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import dev.zacsweers.moshix.adapters.AdaptedBy
import ga.overfullstack.pojo.composite.ConnectGraph.Graph.Records.Records.Record.RecordBody
import ga.overfullstack.pojo.composite.ConnectGraph.Graph.Records.Records.Record.RecordBody.Attributes
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
          @AdaptedBy(RecordBodyAdapter::class)
          data class RecordBody(val attributes: Attributes, val recordBody: Map<String, String>) {
            @JsonClass(generateAdapter = true)
            data class Attributes(val method: String, val type: String)
          }
        }
      }
    }
  }

  class RecordBodyAdapter : JsonAdapter<RecordBody>() {
    @FromJson
    override fun fromJson(reader: JsonReader): RecordBody {
      reader.beginObject()
      var attributes = Attributes("", "")
      val recordBody = mutableMapOf<String, String>()
      while (reader.hasNext()) {
        when (val nextName = reader.nextName()) {
          "attributes" -> {
            val attrMap = reader.readJsonValue() as Map<String, String>
            attributes = Attributes(attrMap["type"] ?: "", attrMap["method"] ?: "")
          }
          else -> recordBody[nextName] = reader.nextString()
        }
      }
      reader.endObject()
      return RecordBody(attributes, recordBody)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, record: RecordBody?) {
      with(writer) {
        obj(record) {
          obj("attributes", attributes) {
            string("type", type)
            string("url", method)
          }
          recordBody.entries.forEach { string(it.key, it.value) }
        }
      }
    }
  }
}
