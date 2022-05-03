package ga.overfullstack

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test
import java.util.Date

class AdapterConvenience {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun lenient() {
    val beanStr = readFileFromTestResource("bean.json")
    val mapAdapter = Moshi.Builder().build().adapter<Bean2>().lenient().serializeNulls()
    val map = mapAdapter.fromJson(beanStr)
    println(map)
  }

  @JsonClass(generateAdapter = true)
  internal data class Bean2(val name: String, val items: List<String>, val extra: Date)
}
