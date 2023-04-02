package ga.overfullstack

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Token.BEGIN_ARRAY
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import java.util.Collections.singletonList

object AnyAdapterFactory : JsonAdapter.Factory {
  override fun create(
    type: Type,
    annotations: Set<Annotation>,
    moshi: Moshi
  ): JsonAdapter<*>? {
    val elementType = Types.collectionElementType(type, List::class.java)
    if (!(Types.getRawType(type) === List::class.java && Types.getRawType(elementType) === Map::class.java)) {
      return null
    }
    val delegateAdapter: JsonAdapter<List<Any?>?> = moshi.adapter(type)
    val singleElementAdapter: JsonAdapter<Any?> = moshi.adapter(elementType)
    return object : JsonAdapter<List<Any?>?>() {
      override fun fromJson(reader: JsonReader): List<Any?>? =
        if (reader.peek() !== BEGIN_ARRAY) {
          singletonList(singleElementAdapter.fromJson(reader))
        } else {
          delegateAdapter.fromJson(reader)
        }

      override fun toJson(writer: JsonWriter, value: List<Any?>?) {
        if (value == null) {
          return
        }
        if (value.size == 1) {
          singleElementAdapter.toJson(writer, value[0])
        } else {
          delegateAdapter.toJson(writer, value)
        }
      }
    }
  }
}
