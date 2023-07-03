package ga.overfullstack.pojo.composite

import arrow.optics.optics
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import ga.overfullstack.NoArg
import ga.overfullstack.pojo.composite.ConnectGraph.Graph.Records
import ga.overfullstack.utils.anyMap
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
        data class Record(val isSetRecordBody: Boolean, val recordBody: Map<String, Any?>?) {
          companion object
        }
      }
    }
  }

  /** fromJson: PQ graph JSON -> Connect Graph POJO toJson: Connect Graph POJO -> PQ Graph JSON */
  object ConnectPQGraphAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): ConnectGraph =
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
                                  Records.Records.Record(true, anyMap())
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
      dynamicJsonAdapter: JsonAdapter<Any>
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
                dynamicJsonAdapter.toJson(writer, record?.recordBody)
              }
            }
          }
        }
      }
  }
}
