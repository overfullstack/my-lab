package ga.overfullstack.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true) data class Body(val records: List<Record>)

@JsonClass(generateAdapter = true)
data class Record(val attributes: Attributes, val recordBody: Map<String, Any>)

@JsonClass(generateAdapter = true) data class Attributes(val type: String, val url: String)

object RecordAdapter {
  private val options = JsonReader.Options.of("attributes")

  @FromJson
  fun fromJson(reader: JsonReader, attributesJsonAdapter: JsonAdapter<Attributes>): Record {
    reader.beginObject()
    var attributes: Attributes? = null
    val recordBody = mutableMapOf<String, Any>()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> {
          if (attributes != null) {
            throw JsonDataException("Duplicate attributes.")
          }
          attributes = attributesJsonAdapter.fromJson(reader)
        }
        -1 -> {
          recordBody[reader.nextName()] = reader.readJsonValue()!!
        }
        else -> {
          throw AssertionError()
        }
      }
    }
    reader.endObject()
    return Record(attributes!!, recordBody)
  }

  @ToJson
  fun toJson(
    writer: JsonWriter,
    value: Record,
    attributesJsonAdapter: JsonAdapter<Attributes>,
    dynamicJsonAdapter: JsonAdapter<Any>
  ) {
    writer.beginObject()
    writer.name("attributes")
    attributesJsonAdapter.toJson(writer, value.attributes)
    for (entry in value.recordBody.entries) {
      writer.name(entry.key)
      dynamicJsonAdapter.toJson(writer, entry.value)
    }
    writer.endObject()
  }
}

fun main() {
  val moshi = Moshi.Builder().add(RecordAdapter).build()
  val idResponseJsonAdapter = moshi.adapter(Body::class.java)
  val encoded =
    """
    {
      "records": [
        {
          "attributes": {
          "type": "...",
          "url": "..."
        },
        "Id": "...",
        "Name": "...",
        "...": "..."
      }
    ]
  }"""
      .trimIndent()
  val decoded =
    Body(
      listOf(
        Record(
          Attributes(type = "...", url = "..."),
          mapOf("Id" to "...", "Name" to "...", "..." to "...")
        )
      )
    )
  println(idResponseJsonAdapter.fromJson(encoded))
  println(idResponseJsonAdapter.toJson(decoded))
}
