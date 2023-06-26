package ga.overfullstack.factory

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.CompositeResponse
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

class RecordAdapterFactoryTest {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read composite response to pojo`() {
    val compositeRepStr = readFileFromTestResource("composite-response.json")
    val compositeRepAdapter = Moshi.Builder().add(RecordAdapterFactory()).build().adapter<CompositeResponse>()
    val compositeResp = compositeRepAdapter.fromJson(compositeRepStr)
    println(compositeResp)
    println(compositeRepAdapter.indent("  ").toJson(compositeResp))
  }
}
