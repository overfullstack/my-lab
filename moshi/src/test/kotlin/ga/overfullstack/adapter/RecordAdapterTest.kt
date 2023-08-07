package ga.overfullstack.adapter

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.jupiter.api.Test

class RecordAdapterTest {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `record adapter`() {
    val recordsAdapter = Moshi.Builder().add(RecordAdapter).build().adapter<Records>()
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
    val records = recordsAdapter.fromJson(encoded)
    println(recordsAdapter.indent("  ").toJson(records))
  }
}
