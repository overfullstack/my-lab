package ga.overfullstack

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ga.overfullstack.pojo.Bean1
import ga.overfullstack.pojo.Message
import ga.overfullstack.pojo.NestedBean
import ga.overfullstack.pojo.NestedBean1
import ga.overfullstack.pojo.NestedMessages
import ga.overfullstack.pojo.Obj
import ga.overfullstack.utils.readFileFromTestResource
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class MoshiLab {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readNestedJsonToNestedPojo() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val mapAdapter = Moshi.Builder().build().adapter<NestedBean>()
    val nestedBean = mapAdapter.fromJson(nestedBeanStr)!!
    nestedBean.bean shouldNotBe null
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToMap() {
    val mapStr = readFileFromTestResource("obj.json")
    val mapAdapter = Moshi.Builder().build().adapter<Map<String, String>>()
    val map = mapAdapter.fromJson(mapStr)
    println(map)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read collection to list map`() {
    val collectionStr = readFileFromTestResource("collection.json")
    val mapAdapter = Moshi.Builder().build().adapter<Any?>()
    val collection = mapAdapter.fromJson(collectionStr)
    println(collection)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read Json into Map with star projection`() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val mapAdapter = Moshi.Builder().build().adapter<Map<String, *>>()
    val any = mapAdapter.fromJson(nestedBeanStr)!!
    (any["bean"] as Map<String, *>) shouldContainExactly
      mapOf("name" to "member", "items" to listOf("item1", "item2"))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read json to pojo`() {
    val objStr = readFileFromTestResource("obj.json")
    val mapAdapter = Moshi.Builder().build().adapter<Obj>()
    val obj = mapAdapter.fromJson(objStr)!!
    obj.a shouldBeEqual "1"
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToAny() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val anyAdapter = Moshi.Builder().build().adapter<Any>()
    val any =
      anyAdapter.fromJson(nestedBeanStr)!! // It reads to LinkedHashTreeMap like javascript JSON
    println(any)
  }

  @Test
  fun collectionElementType() {
    val strList = listOf("a", "b")
    println(Types.collectionElementType(strList::class.java, List::class.java))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read json to pojo without Annotations`() {
    val mapAdapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter<Bean1>()
    val bean = mapAdapter.fromJson(readFileFromTestResource("bean.json"))!!
    println(bean)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read Json To nested Pojo`() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val mapAdapter =
      Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter<NestedBean1>()
    val nestedBean = mapAdapter.fromJson(nestedBeanStr)!!
    println(nestedBean)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read Json To nested Pojo with Custom Adapter`() {
    val nestedMessages =
      Moshi.Builder()
        .add(NestedMessagesAdapter(3))
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter<NestedMessages>()
        .fromJson(readFileFromTestResource("msg.json"))!!
    println(nestedMessages)
  }

  class NestedMessagesAdapter(private val msgCount: Int) {
    @FromJson
    fun fromJson(message: Message): NestedMessages {
      val msgs = List(msgCount) { message.copy(message = it.toString()) }
      return NestedMessages(msgs)
    }
  }
}
