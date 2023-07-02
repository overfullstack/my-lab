package ga.overfullstack.pojo.composite

import arrow.optics.optics
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import ga.overfullstack.NoArg
import ga.overfullstack.pojo.composite.ConnectGraph.Graph.Records
import ga.overfullstack.pojo.composite.ConnectGraph.Graph.Records.Records.Record.RecordBody
import ga.overfullstack.pojo.composite.ConnectGraph.Graph.Records.Records.Record.RecordBody.Attributes
import ga.overfullstack.utils.instanceWithJavaReflectionFn
import ga.overfullstack.utils.listr
import ga.overfullstack.utils.objr
import org.http4k.format.list
import org.http4k.format.obj
import org.http4k.format.string

@NoArg
@optics
@JsonClass(generateAdapter = true)
data class ConnectGraph(
  val graph: Graph?,
  val isSetGraph: Boolean,
  val isSetPricingPref: Boolean,
  val pricingPref: String?
) {
  companion object

  @NoArg
  @optics
  @JsonClass(generateAdapter = true)
  data class Graph(
    val graphId: String?,
    val isSetGraphId: Boolean,
    val isSetRecords: Boolean,
    val records: Records?
  ) {
    companion object

    @optics
    @JsonClass(generateAdapter = true)
    data class Records(val isSetRecordsList: Boolean, val recordsList: List<Records>) {
      companion object

      @NoArg
      @optics
      @JsonClass(generateAdapter = true)
      data class Records(
        val isSetReferenceId: Boolean,
        val referenceId: String?,
        val isSetRecord: Boolean,
        val record: Record?
      ) {
        companion object

        @NoArg
        @optics
        @JsonClass(generateAdapter = true)
        data class Record(val isSetRecordBody: Boolean, val recordBody: RecordBody?) {
          companion object

          @optics
          @JsonClass(generateAdapter = true)
          data class RecordBody(val attributes: Attributes?, val recordBody: Map<String, String>?) {
            companion object

            @optics
            @JsonClass(generateAdapter = true)
            data class Attributes(val method: String?, val type: String?) {
              companion object
            }
          }
        }
      }
    }
  }

  object RecordBodyAdapter {
    private val options = JsonReader.Options.of("attributes")

    @FromJson
    fun fromJson(reader: JsonReader, attributesJsonAdapter: JsonAdapter<Attributes>): RecordBody {
      reader.beginObject()
      var attributes: Attributes? = null
      val recordBody = mutableMapOf<String, String>()
      while (reader.hasNext()) {
        when (reader.selectName(options)) {
          0 -> {
            if (attributes != null) {
              throw JsonDataException("Duplicate attributes.")
            }
            attributes = attributesJsonAdapter.fromJson(reader)
          }
          -1 -> recordBody[reader.nextName()] = reader.nextString()!!
          else -> {
            throw AssertionError()
          }
        }
      }
      reader.endObject()
      return RecordBody(attributes!!, recordBody)
    }

    @ToJson
    fun toJson(
      writer: JsonWriter,
      recordBody: RecordBody?,
      attributesJsonAdapter: JsonAdapter<Attributes>
    ) =
      with(writer) {
        obj(recordBody) {
          name("attributes")
          attributesJsonAdapter.toJson(writer, recordBody?.attributes)
          this.recordBody?.entries?.forEach { string(it.key, it.value) }
        }
      }
  }

  /**
   * Connect Graph POJO -> PQ Graph JSON
   * PQ graph JSON -> Connect Graph POJO
   */
  object ConnectPQGraphAdapter {
    @FromJson
    fun fromJson(reader: JsonReader, recordBodyAdapter: JsonAdapter<RecordBody>): ConnectGraph =
      with(reader) {
        objr(instanceWithJavaReflectionFn<ConnectGraph>()) {
          when (it) {
            "pricingPref" ->
              ConnectGraph.isSetPricingPref.set(
                ConnectGraph.pricingPref.set(this, nextString()),
                true
              )
            "graph" ->
              ConnectGraph.graph.set(
                ConnectGraph.isSetGraph.set(this, true),
                objr(instanceWithJavaReflectionFn<Graph>()) {
                  when (it) {
                    "graphId" -> Graph.graphId.set(Graph.isSetGraphId.set(this, true), nextString())
                    "records" ->
                      Graph.records.set(
                        Graph.isSetRecords.set(this, true),
                        Records(
                          true,
                          listr(instanceWithJavaReflectionFn<Records.Records>()) {
                            when (it) {
                              "referenceId" ->
                                Records.Records.referenceId.set(
                                  Records.Records.isSetReferenceId.set(this, true),
                                  nextString()
                                )
                              "record" ->
                                Records.Records.record.set(
                                  Records.Records.isSetRecord.set(this, true),
                                  Records.Records.Record(true, recordBodyAdapter.fromJson(reader)!!)
                                )
                              else -> this.also { skipValue() }
                            }
                          }
                        )
                      )
                    else -> this.also { skipValue() }
                  }
                }
              )
            else -> this.also { skipValue() }
          }
        }
      }

    @ToJson
    fun toJson(
      writer: JsonWriter,
      connectGraph: ConnectGraph?,
      recordBodyAdapter: JsonAdapter<RecordBody>
    ) =
      with(writer) {
        obj(connectGraph) {
          string("pricingPref", pricingPref)
          obj("graph", graph) {
            string("graphId", graphId)
            list("records", records?.recordsList) {
              obj(this) {
                string("referenceId", referenceId)
                name("record")
                recordBodyAdapter.toJson(writer, record?.recordBody)
              }
            }
          }
        }
      }
  }
}
