package ga.overfullstack

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import dev.zacsweers.moshix.adapters.AdaptedBy
import dev.zacsweers.moshix.adapters.JsonString
import ga.overfullstack.utils.readFileFromTestResource
import okio.BufferedSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.IOException
import java.lang.reflect.Type
import java.util.Locale

internal class MoshiLab {
  @Test
  @Throws(IOException::class)
  fun upperCaseFactory() {
    val beanStr = readFileFromTestResource("nested-bean.json")
    val moshi = Moshi.Builder().add(UppercaseAdapterFactory()).build()
    val beanJsonAdapter = moshi.adapter(NestedBean::class.java)
    val nestedBean = beanJsonAdapter.fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    assertEquals("container".uppercase(), nestedBean?.name)
    assertEquals("member".uppercase(), nestedBean?.bean?.name)
    assertEquals(2, nestedBean?.bean?.items?.size)
  }

  @Test
  @Throws(IOException::class)
  fun regexFactory() {
    val beanStr = readFileFromTestResource("nested-bean.json")
    val moshi = Moshi.Builder().add(RegexAdapterFactory()).build()
    val beanJsonAdapter = moshi.adapter(NestedBean::class.java)
    val nestedBean = beanJsonAdapter.fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    assertEquals("{{container}}".uppercase(), nestedBean?.name)
    assertEquals("member", nestedBean?.bean?.name)
    assertEquals(2, nestedBean?.bean?.items?.size)
    assertEquals("{{item1}}".uppercase(), nestedBean?.bean?.items?.get(0))
    assertEquals("item2", nestedBean?.bean?.items?.get(1))
  }

  @Test
  fun jsonString() {
    val collectionJsonStr = readFileFromTestResource("collection.json")
    val collectionAdapter = Moshi.Builder()
      .add(JsonString.Factory())
      .add(AdaptedBy.Factory())
      .build()
      .adapter(Collection::class.java)
    println(collectionAdapter.fromJson(collectionJsonStr))
  }

  @JsonClass(generateAdapter = true)
  internal data class Bean(val name: String, val items: List<String>)

  @JsonClass(generateAdapter = true)
  internal data class NestedBean(val name: String, val bean: Bean)

  internal class UppercaseAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (type != String::class.java) {
        return null
      }
      val stringAdapter = moshi.nextAdapter<String>(this, String::class.java, Util.NO_ANNOTATIONS)
      return object : JsonAdapter<String>() {
        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): String {
          val s = stringAdapter.fromJson(reader)
          return s?.uppercase() ?: ""
        }

        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: String?) {
          stringAdapter.toJson(writer, value?.uppercase(Locale.getDefault()))
        }
      }
    }
  }

  internal class RegexAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (type != String::class.java) {
        return null
      }
      val stringAdapter = moshi.nextAdapter<String>(this, String::class.java, Util.NO_ANNOTATIONS)
      return object : JsonAdapter<String>() {
        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): String? {
          val s = stringAdapter.fromJson(reader)
          val postManExp = "\\{\\{[^}]+}}".toRegex()
          return s?.let { postManExp.replace(s) { matchResult -> matchResult.value.uppercase() } }
        }

        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: String?) {
          stringAdapter.toJson(writer, value?.uppercase(Locale.getDefault()))
        }
      }
    }
  }

  @JsonClass(generateAdapter = true)
  @AdaptedBy(ItemDataAdapter::class)
  internal data class ItemData(val data: String)

  class ItemDataAdapter : JsonAdapter<ItemData>() {
    override fun fromJson(reader: JsonReader): ItemData {
      return ItemData(reader.nextSource().use(BufferedSource::readUtf8))
    }

    override fun toJson(writer: JsonWriter, itemData: ItemData?) {
      writer.valueSink().use { sink -> sink.writeUtf8(checkNotNull(itemData?.data)) }
    }
  }

  @JsonClass(generateAdapter = true)
  internal data class Collection(val name: String, val itemData: List<ItemData>)

}
