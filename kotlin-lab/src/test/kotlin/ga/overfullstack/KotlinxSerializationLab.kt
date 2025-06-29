package ga.overfullstack

import com.salesforce.revoman.input.readFileInResourcesToString
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import okio.FileSystem
import okio.Path.Companion.toPath
import org.junit.jupiter.api.Test

private const val BTI_COUNT = 15000

class KotlinxSerializationLab {
  @OptIn(ExperimentalSerializationApi::class)
  @Test
  fun `generate bt json`() {
    val bt = Json.parseToJsonElement(readFileInResourcesToString("json/bt-multi-action.json"))
    val billingTransactionItem =
      bt.jsonObject["BillingTransaction"]!!
        .jsonArray[0]
        .jsonObject["BillingTransactionItem"]!!
        .jsonArray
    val btis = (1..(BTI_COUNT / 4)).flatMap { billingTransactionItem }
    val inflatedBT = buildJsonArray { addAll(btis) }
    // println(bt2)
    FileSystem.SYSTEM.write("bt-$BTI_COUNT.json".toPath(), true) {
      writeUtf8(inflatedBT.toString())
    }
  }
}
