package ga.overfullstack.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonAdapter.Factory
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReader.Options
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.rawType
import java.lang.reflect.Type

class BiAdapterFactory<T>
internal constructor(
  private val baseType: Class<T>,
  private val labelKey: String,
  private val labelValueForSuccess: Boolean,
  private val successType: Type,
  private val errorType: Type,
) : Factory {
  override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<*>? {
    if (type.rawType != baseType || annotations.isNotEmpty()) {
      return null
    }
    return BiAdapter(
        labelKey,
        Triple(labelValueForSuccess, successType, moshi.adapter(successType)),
        errorType to moshi.adapter(errorType)
      )
      .nullSafe()
  }

  internal class BiAdapter(
    private val labelKey: String,
    private val successAdapter: Triple<Boolean, Type, JsonAdapter<Any>>,
    private val errorAdapter: Pair<Type, JsonAdapter<Any>>,
  ) : JsonAdapter<Any>() {
    override fun fromJson(reader: JsonReader): Any? {
      val peeked = reader.peekJson()
      val labelValue = peeked.use(::labelValue)
      val jsonAdapter =
        if (successAdapter.first == labelValue) successAdapter.third else errorAdapter.second
      return jsonAdapter.fromJson(reader)
    }

    private fun labelValue(reader: JsonReader): Boolean {
      reader.beginObject()
      while (reader.hasNext()) {
        if (reader.selectName(Options.of(labelKey)) == -1) {
          reader.skipName()
          reader.skipValue()
          continue
        }
        return reader.nextBoolean()
      }
      throw JsonDataException("Missing label for $labelKey")
    }

    override fun toJson(writer: JsonWriter, value: Any?) {
      val type: Class<*> = value!!.javaClass
      when (type) {
        successAdapter.second -> successAdapter.third.toJson(writer, value)
        errorAdapter.first -> errorAdapter.second.toJson(writer, value)
      }
    }
  }

  override fun toString(): String {
    return "BiAdapterFactory($labelKey)"
  }

  companion object {
    @JvmStatic
    fun <T> of(
      baseType: Class<T>,
      labelKey: String,
      labelValueForSuccess: Boolean,
      successType: Class<out T>,
      errorType: Class<out T>
    ): BiAdapterFactory<T> {
      return BiAdapterFactory(
        baseType,
        labelKey,
        labelValueForSuccess,
        successType,
        errorType,
      )
    }
  }
}
