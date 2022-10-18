package ga.overfullstack

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Token.BEGIN_ARRAY
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.PROPERTY

@Retention(RUNTIME)
@Target(PROPERTY)
@JsonQualifier
annotation class ObjOrList

object ObjOrListAdapterFactory : JsonAdapter.Factory {
  override fun create(
    type: Type,
    annotations: Set<Annotation>,
    moshi: Moshi
  ): JsonAdapter<*>? {
    val delegateAnnotations = Types.nextAnnotations(annotations, ObjOrList::class.java) ?: return null
    if (Types.getRawType(type) !== List::class.java) {
      throw IllegalArgumentException("@SingleOrList requires the type to be List. Found this type: $type")
    }
    val elementType = Types.collectionElementType(type, List::class.java)
    val delegateAdapter: JsonAdapter<List<Any?>?> = moshi.adapter(type, delegateAnnotations)
    val singleElementAdapter: JsonAdapter<Any?> = moshi.adapter(elementType)
    return object : JsonAdapter<Any?>() {
      override fun fromJson(reader: JsonReader): Any? =
        if (reader.peek() !== BEGIN_ARRAY) {
          singleElementAdapter.fromJson(reader)
        } else {
          delegateAdapter.fromJson(reader)
        }
      override fun toJson(writer: JsonWriter, value: Any?) {
        if (value == null) {
          return
        }
        if (value is List<*>) {
          delegateAdapter.toJson(writer, value)
        } else {
          singleElementAdapter.toJson(writer, value)
        }
      }
    }
  }
}
