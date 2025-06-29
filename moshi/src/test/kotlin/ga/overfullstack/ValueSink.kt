package ga.overfullstack

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.internal.Util
import ga.overfullstack.pojo.Bean
import ga.overfullstack.pojo.NestedBean
import java.lang.reflect.Type
import java.nio.charset.Charset
import org.junit.jupiter.api.Test

internal class ValueSink {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun upperCaseFactoryToJson() {
    val beanJsonAdapter = Moshi.Builder().add(AppendTypeFactory()).build().adapter<NestedBean>()
    val nestedBeanStr =
      beanJsonAdapter.toJson(NestedBean("container", Bean("member", listOf("item1", "item2"))))
    println(nestedBeanStr)
  }

  private class AppendTypeFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (type != String::class.java) {
        return null
      }
      val stringAdapter = moshi.nextAdapter<String>(this, String::class.java, Util.NO_ANNOTATIONS)
      return object : JsonAdapter<String>() {
        override fun fromJson(reader: JsonReader): String? {
          return stringAdapter.fromJson(reader)
        }

        override fun toJson(writer: JsonWriter, value: String?) {
          writer.valueSink().use { it.writeString("type:\"string\"", Charset.defaultCharset()) }
        }
      }
    }
  }
}
