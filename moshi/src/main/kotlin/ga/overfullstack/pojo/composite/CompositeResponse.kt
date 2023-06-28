package ga.overfullstack.pojo.composite


import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import ga.overfullstack.pojo.composite.CompositeResponse.CompositeResponse.Body.Record
import ga.overfullstack.pojo.composite.CompositeResponse.CompositeResponse.Body.Record.Attributes
import org.http4k.format.obj
import org.http4k.format.string

@JsonClass(generateAdapter = true)
data class CompositeResponse(
  val compositeResponse: List<CompositeResponse>
) {
  @JsonClass(generateAdapter = true)
  data class CompositeResponse(
    val body: Body,
    val httpHeaders: HttpHeaders,
    val httpStatusCode: Int,
    val referenceId: String
  ) {
    @JsonClass(generateAdapter = true)
    data class Body(
      val done: Boolean,
      val records: List<Record>,
      val totalSize: Int
    ) {
      @JsonClass(generateAdapter = true)
      class Record(
        val attributes: Attributes,
        val recordBody: Map<String, String>
      ) {
        @JsonClass(generateAdapter = true)
        class Attributes(
          val type: String,
          val url: String
        )
      }
    }

    @JsonClass(generateAdapter = true)
    class HttpHeaders
  }

  class RecordAdapterFactory : JsonAdapter<Record>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Record {
      reader.beginObject()
      var attributes = Attributes("", "")
      val recordBody = mutableMapOf<String, String>()
      while (reader.hasNext()) {
        when (val nextName = reader.nextName()) {
          "attributes" -> {
            val attrMap = reader.readJsonValue() as Map<String, String>
            attributes = Attributes(attrMap["type"] ?: "", attrMap["url"] ?: "")
          }
          else -> recordBody[nextName] = reader.nextString()
        }
      }
      reader.endObject()
      return Record(attributes, recordBody)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, record: Record?) {
      with(writer) {
        obj(record) {
          obj("attributes", attributes) {
            string("type", type)
            string("url", url)
          }
          obj(recordBody) {
            entries.forEach { string(it.key, it.value) }
          }
        }
      }
    }
  }
}

