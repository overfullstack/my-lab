package ga.overfullstack.pojo.composite

import arrow.optics.optics
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapter
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
import java.lang.reflect.Type

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
    override fun toJson(writer: JsonWriter, recordBody: RecordBody?) {
      with(writer) {
        obj(recordBody) {
          obj("attributes", attributes) {
            string("type", type)
            string("url", method)
          }
          this.recordBody?.entries?.forEach { string(it.key, it.value) }
        }
      }
    }
  }

  class ConnectGraphToPQJsonAdapter(private val recordBodyAdapter: JsonAdapter<RecordBody>) :
    JsonAdapter<ConnectGraph>() {
    @FromJson
    override fun fromJson(reader: JsonReader): ConnectGraph =
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
                        this,
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
    override fun toJson(writer: JsonWriter, connectGraph: ConnectGraph?) =
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

  class ConnectGraphFactory : JsonAdapter.Factory {
    @OptIn(ExperimentalStdlibApi::class)
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (annotations.isNotEmpty()) {
        return null // Annotations? This factory doesn't apply.
      }
      val recordBodyAdapter = moshi.adapter<RecordBody>()
      return ConnectGraphToPQJsonAdapter(recordBodyAdapter).nullSafe()
    }
  }
}
