package ga.overfullstack

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.utils.readFileFromTestResource
import io.kotest.matchers.maps.shouldContainExactly
import org.junit.jupiter.api.Test

internal class MoshiLab {
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
  fun readCollectionToListMap() {
    val collectionStr = readFileFromTestResource("collection.json")
    val mapAdapter = Moshi.Builder().build().adapter<Any?>()
    val collection = mapAdapter.fromJson(collectionStr)
    println(collection)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToAnyMap() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val mapAdapter = Moshi.Builder().build().adapter<Map<*, *>>()
    val any = mapAdapter.fromJson(nestedBeanStr)!! 
    (any["bean"] as Map<String, *>) shouldContainExactly mapOf("name" to "member", "items" to listOf("item1", "item2"))
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToAny() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val anyAdapter = Moshi.Builder().build().adapter<Any>()
    val any = anyAdapter.fromJson(nestedBeanStr)!! // It reads to LinkedHashTreeMap like javascript JSON
    println(any)
  }
}
