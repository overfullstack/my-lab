package ga.overfullstack.utils.json

import com.squareup.moshi.JsonReader
import java.util.function.BiConsumer

fun nextString(reader: JsonReader): String = reader.nextString()

fun nextBoolean(reader: JsonReader): Boolean = reader.nextBoolean()

fun nextInt(reader: JsonReader): Int = reader.nextInt()

fun nextLong(reader: JsonReader): Long = reader.nextLong()

fun skipValue(reader: JsonReader) = reader.skipValue()

fun nextName(reader: JsonReader): String = reader.nextName()

fun <T> obj(mk: () -> T, reader: JsonReader, block: BiConsumer<T, String>): T =
  with(reader) {
    beginObject()
    val item = mk()
    while (hasNext()) {
      block.accept(item, nextName())
    }
    endObject()
    item
  }

fun <T> list(mk: () -> T, reader: JsonReader, block: BiConsumer<T, String>): List<T?>? =
  reader.skipNullOr {
    val items = mutableListOf<T?>()
    beginArray()
    while (hasNext()) items += obj(mk, this, block)
    endArray()
    items
  }

private fun <T> JsonReader.skipNullOr(fn: JsonReader.() -> T): T? =
  if (peek() == JsonReader.Token.NULL) skipValue().let { null } else fn()

fun JsonReader.anyMap(): Map<String, Any?>? = skipNullOr {
  beginObject()
  val map = mutableMapOf<String, Any?>()
  while (hasNext()) map += nextName() to readJsonValue()
  endObject()
  map
}

fun <T> JsonReader.objr(mk: () -> T, fn: T.(String) -> T): T {
  beginObject()
  var item = mk()
  while (hasNext()) {
    item = item.fn(nextName())
  }
  endObject()
  return item
}

fun <T> JsonReader.listr(mk: () -> T, item: T.(String) -> T): List<T> {
  val items = mutableListOf<T>()
  beginArray()
  while (hasNext()) {
    items += objr(mk, item)
  }
  endArray()
  return items
}
