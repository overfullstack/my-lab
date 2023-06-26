package ga.overfullstack.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import java.lang.reflect.Type
import java.util.Locale

class UppercaseAdapterFactory : JsonAdapter.Factory {
  override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
    if (type != String::class.java) {
      return null
    }
    val stringAdapter = moshi.nextAdapter<String>(this, String::class.java, Util.NO_ANNOTATIONS)
    return object : JsonAdapter<String>() {
      override fun fromJson(reader: JsonReader): String {
        val s = stringAdapter.fromJson(reader)
        return s?.uppercase() ?: ""
      }

      override fun toJson(writer: JsonWriter, value: String?) = 
        stringAdapter.toJson(writer, value?.uppercase(Locale.getDefault()))
    }
  }
}
