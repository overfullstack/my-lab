package ga.overfullstack.adapter

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.jupiter.api.Test

class RecordAdapterTest {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `record adapter`() {
    val bodyAdapter = Moshi.Builder().add(RecordAdapter).build().adapter<Body>()
    val encoded =
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
    val body = bodyAdapter.fromJson(encoded)
    println(bodyAdapter.indent("  ").toJson(body))
  }
}
