package ga.overfullstack

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

internal class MoshiLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToMap() {
    val beanStr = readFileFromTestResource("map.json")
    val mapAdapter = Moshi.Builder().build().adapter<Map<String, String>>()
    val map = mapAdapter.fromJson(beanStr)
    println(map)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToAny() {
    val beanStr = readFileFromTestResource("nested-bean.json")
    val anyAdapter = Moshi.Builder().build().adapter<Any>()
    val any = anyAdapter.fromJson(beanStr)
    println(any) // It reads to map like javascript JSON
  }
}
