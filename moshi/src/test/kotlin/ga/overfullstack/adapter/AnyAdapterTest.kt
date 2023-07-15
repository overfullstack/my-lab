package ga.overfullstack.adapter

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.jupiter.api.Test

class AnyAdapterTest {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `any adapter`() {
    val jsonStr =
      """
    {
      "records": [
        {
          "Id": "4",
          "attributes": {
            "skill": "kill",
            "weapon": "gun"
          },
          "Name": "John Wick",
          "Role": "Assassin"
        }
      ]
    }"""
        .trimIndent()
    val anyAdapter = Moshi.Builder().add(AnyAdapterFactory).build().adapter<Any>()
    val jsonMap = anyAdapter.fromJson(jsonStr)
    println(anyAdapter.toJson(jsonMap))
  }
}
