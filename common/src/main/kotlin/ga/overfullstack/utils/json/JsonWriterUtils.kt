@file:JvmName("JsonWriterUtils")

package ga.overfullstack.utils.json

import com.squareup.moshi.JsonWriter
import java.util.function.Consumer

fun <T> obj(name: String, obj: T, writer: JsonWriter, block: Consumer<T>) =
  with(writer) {
    name(name)
    obj(obj, writer, block)
  }

fun <T> obj(obj: T, writer: JsonWriter, block: Consumer<T>): Unit =
  with(writer) {
    if (obj == null) {
      nullValue()
    } else {
      beginObject()
      block.accept(obj)
      endObject()
    }
  }

fun bool(name: String, value: Boolean?, writer: JsonWriter): Any =
  with(writer) {
    name(name)
    value?.also(::value) ?: nullValue()
  }

fun string(name: String, value: String?, writer: JsonWriter): Any =
  with(writer) {
    name(name)
    value?.also(::value) ?: nullValue()
  }

fun <T> list(name: String, list: List<T>?, writer: JsonWriter, block: Consumer<T>): JsonWriter =
  with(writer) {
    name(name)
    list(list, this, block)
  }!!

fun <T> list(list: List<T>?, writer: JsonWriter, block: Consumer<T>): JsonWriter =
  with(writer) {
    when (list) {
      null -> nullValue()
      else -> {
        beginArray()
        list.forEach(block)
        endArray()
      }
    }
  }
