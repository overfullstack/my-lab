package ga.overfullstack.pojo.composite


import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import ga.overfullstack.pojo.composite.CompositeGraphPQ.Graph.Record.Record
import ga.overfullstack.pojo.composite.CompositeGraphPQ.Graph.Record.Record.Attributes
import java.lang.reflect.Type

@JsonClass(generateAdapter = true)
data class CompositeGraphPQ(
  val graph: Graph,
  val pricingPref: String
) {
  @JsonClass(generateAdapter = true)
  data class Graph(
    val graphId: String,
    val records: List<Record>
  ) {
    @JsonClass(generateAdapter = true)
    data class Record(
      val record: Record,
      val referenceId: String
    ) {
      @JsonClass(generateAdapter = true)
      data class Record(
        val attributes: Attributes,
        val recordBody: Map<String, String>
      ) {
        @JsonClass(generateAdapter = true)
        data class Attributes(
          val type: String,
          val method: String
        )
      }
    }
  }

  class RecordAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (type != Record::class.java) {
        return null
      }
      val attributeAdapter = moshi.nextAdapter<Attributes>(this, Attributes::class.java, Util.NO_ANNOTATIONS)
      return object : JsonAdapter<Record>() {
        override fun fromJson(reader: JsonReader): Record {
          reader.beginObject()
          var attributes = Attributes("", "")
          val recordBody = mutableMapOf<String, String>()
          while (reader.hasNext()) {
            when (val nextName = reader.nextName()) {
              "attributes" -> attributes = attributeAdapter.fromJson(reader.nextString()) ?: Attributes("", "")
              else -> recordBody[nextName] = reader.nextString()
            }
          }
          return Record(attributes, recordBody)
        }

        override fun toJson(writer: JsonWriter, value: Record?) {
          writer.beginObject()
          writer.name("attributes").value(attributeAdapter.toJson(value?.attributes))
          value?.recordBody?.entries?.forEach { writer.name(it.key).value(it.value) }
          writer.endObject()
        }
      }
    }
  }
}
