package ga.overfullstack

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

class PostmanLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `parse postman environment`() {
    val whiteListToRetain = setOf("standardPricebookId", "adminUserId", "standardUserId", "standardUserProfileId")
    val anyAdapter = Moshi.Builder().build().adapter<Environment>()
    val env =
      anyAdapter.fromJson(readFileFromTestResource("postman/linux-sm.postman_environment.json"))
    env!!.values.asSequence()
      .filter { (key) -> !whiteListToRetain.contains(key) && !key.endsWith("PsgId") && !key.endsWith("UserId") && key.endsWith("Id") }
      .forEach { (key, value) -> println("$key: $value") }
  }
}

@JsonClass(generateAdapter = true)
internal data class Environment(val values: List<EnvValue>)

@JsonClass(generateAdapter = true)
internal data class EnvValue(
  val key: String,
  val value: String?,
  val enabled: Boolean
)
