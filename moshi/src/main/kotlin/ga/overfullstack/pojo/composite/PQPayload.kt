package ga.overfullstack.pojo.composite

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import ga.overfullstack.pojo.composite.PQPayload.Graph.Record.Record
import ga.overfullstack.pojo.composite.PQPayload.Graph.Record.Record.Attributes
import org.http4k.format.boolean
import org.http4k.format.list
import org.http4k.format.obj
import org.http4k.format.string

@JsonClass(generateAdapter = true)
data class PQPayload(val graph: Graph, val pricingPref: String) {
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

  object RecordAdapter {
    private val options = JsonReader.Options.of("attributes")

    @FromJson
    fun fromJson(reader: JsonReader, attributesJsonAdapter: JsonAdapter<Attributes>): Record {
      reader.beginObject()
      var attributes: Attributes? = null
      val recordBody = mutableMapOf<String, String>()
      while (reader.hasNext()) {
        when (reader.selectName(options)) {
          0 -> {
            if (attributes != null) {
              throw JsonDataException("Duplicate attributes")
            }
            attributes = attributesJsonAdapter.fromJson(reader)
          }
          -1 -> recordBody[reader.nextName()] = reader.nextString()!!
          else -> throw AssertionError()
        }
      }
      reader.endObject()
      return Record(attributes!!, recordBody)
    }

    @ToJson
    fun toJson(
      writer: JsonWriter,
      record: Record?,
      attributesJsonAdapter: JsonAdapter<Attributes>
    ) =
      with(writer) {
        obj(record) {
          name("attributes")
          attributesJsonAdapter.toJson(writer, record?.attributes)
          this.recordBody.entries.forEach { string(it.key, it.value) }
        }
      }
  }

  /** PQ payload POJO -> Connect graph JSON | Connect graph JSON -> PQ graph POJO */
  object PQPayloadGraphAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, pqPayload: PQPayload?, recordAdapter: JsonAdapter<Record>) =
      with(writer) {
        obj(pqPayload) {
          boolean("isSetPricingPref", true)
          string("pricingPref", pricingPref)
          boolean("isSetGraph", true)
          obj("graph", graph) {
            boolean("isSetGraphId", true)
            string("graphId", graphId)
            boolean("isSetRecords", true)
            obj("records", records) {
              boolean("isSetRecordsList", true)
              list("recordsList", this) {
                obj(this) {
                  boolean("isSetReferenceId", true)
                  string("referenceId", referenceId)
                  boolean("isSetRecord", true)
                  obj("record", record) {
                    boolean("isSetRecordBody", true)
                    name("recordBody")
                    recordAdapter.toJson(writer, this)
                  }
                }
              }
            }
          }
        }
      }
  }
}
