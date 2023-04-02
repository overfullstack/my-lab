package ga.overfullstack.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.internal.Util
import ga.overfullstack.state.NestedBean
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException
import java.lang.reflect.Type
import java.util.Locale

class RegexFactory {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  @Throws(IOException::class)
  fun regexFactory() {
    val beanStr = readFileFromTestResource("bean-with-regex.json")
    val moshi = Moshi.Builder().add(RegexAdapterFactory()).build()
    val beanJsonAdapter = moshi.adapter<NestedBean>()
    val nestedBean = beanJsonAdapter.fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    Assertions.assertEquals("{{container}}".uppercase(), nestedBean?.name)
    Assertions.assertEquals("member", nestedBean?.bean?.name)
    Assertions.assertEquals(2, nestedBean?.bean?.items?.size)
    Assertions.assertEquals("{{item1}}".uppercase(), nestedBean?.bean?.items?.get(0))
    Assertions.assertEquals("item2", nestedBean?.bean?.items?.get(1))
  }

  private class RegexAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (type != String::class.java) {
        return null
      }
      val stringAdapter = moshi.nextAdapter<String>(this, String::class.java, Util.NO_ANNOTATIONS)
      return object : JsonAdapter<String>() {
        override fun fromJson(reader: JsonReader): String? {
          val s = stringAdapter.fromJson(reader)
          val postManExp = "\\{\\{[^}]+}}".toRegex()
          return s?.let { postManExp.replace(s) { matchResult -> matchResult.value.uppercase() } }
        }

        override fun toJson(writer: JsonWriter, value: String?) {
          stringAdapter.toJson(writer, value?.uppercase(Locale.getDefault()))
        }
      }
    }
  }
}
