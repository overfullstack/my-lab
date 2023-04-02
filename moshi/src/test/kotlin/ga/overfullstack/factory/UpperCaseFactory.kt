package ga.overfullstack.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.internal.Util
import ga.overfullstack.state.Bean
import ga.overfullstack.state.NestedBean
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.reflect.Type
import java.util.Locale

internal class UpperCaseFactory {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun upperCaseFactoryFromJson() {
    val beanStr = readFileFromTestResource("nested-bean.json")
    val beanJsonAdapter = Moshi.Builder().add(UppercaseAdapterFactory()).build().adapter<NestedBean>()
    val nestedBean = beanJsonAdapter.fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    assertEquals("container".uppercase(), nestedBean?.name)
    assertEquals("member".uppercase(), nestedBean?.bean?.name)
    assertEquals(2, nestedBean?.bean?.items?.size)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun upperCaseFactoryToJson() {
    val beanJsonAdapter = Moshi.Builder().add(UppercaseAdapterFactory()).build().adapter<NestedBean>()
    val nestedBeanStr = beanJsonAdapter.toJson(NestedBean("container", Bean("member", listOf("item1", "item2"))))
    println(nestedBeanStr)
  }

  private class UppercaseAdapterFactory : JsonAdapter.Factory {
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

        override fun toJson(writer: JsonWriter, value: String?) {
          stringAdapter.toJson(writer, value?.uppercase(Locale.getDefault()))
        }
      }
    }
  }

}
