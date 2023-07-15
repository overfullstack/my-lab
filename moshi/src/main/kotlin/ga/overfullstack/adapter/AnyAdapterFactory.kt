package ga.overfullstack.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

object AnyAdapterFactory : JsonAdapter.Factory {
  override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<*> {
    val delegate: JsonAdapter<Any> = moshi.nextAdapter(this, type, annotations)
    return object : JsonAdapter<Any>() {
      override fun fromJson(reader: JsonReader): Any? {
        return delegate.fromJson(reader)
      }

      override fun toJson(writer: JsonWriter, value: Any?) {
        if (writer.path == "\$.records") {
          val newValue = List(3) { _ -> (value as List<Any>)[0] }
          delegate.toJson(writer, newValue)
        } else {
          delegate.toJson(writer, value)
        }
      }
    }
  }
}
