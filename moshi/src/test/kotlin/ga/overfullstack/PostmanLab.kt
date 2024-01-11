package ga.overfullstack

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.utils.readFileInResourcesToString
import org.junit.jupiter.api.Test

class PostmanLab {
  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `parse postman environment`() {
    val whiteListToRetain =
      setOf("standardPricebookId", "adminUserId", "standardUserId", "standardUserProfileId")
    val pmEnvAdapter = Moshi.Builder().build().adapter<Environment>()
    val env =
      pmEnvAdapter.fromJson(
        readFileInResourcesToString("postman/linux-sm.postman_environment.json")
      )
    env!!
      .values
      .asSequence()
      .filter { (key) ->
        !whiteListToRetain.contains(key) &&
          !key.endsWith("PsgId") &&
          !key.endsWith("UserId") &&
          key.endsWith("Id")
      }
      .forEach { (key, value) -> println("$key: $value") }
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `override env from file`() {
    val userKeys =
      setOf(
        "taxAdminUserName",
        "billingAdminUserName",
        "productAndPricingAdminUserName",
        "salesRepUserName"
      )
    val anyAdapter = Moshi.Builder().build().adapter<Any>()
    val env =
      anyAdapter.fromJson(
        readFileInResourcesToString("postman/pq-user-env.postman_environment.json")
      ) as Map<String, Any>
    val values = env.get("values") as List<Map<String, String>>
    val envMap = values.asSequence().map { it["key"] to it["value"] }.toMap()
    val epoch = System.currentTimeMillis()
    val overriddenMap =
      envMap
        .asSequence()
        .map {
          if (userKeys.contains(it.key))
            it.key to
              it.value?.let { targetStr ->
                targetStr.insertStringAtIndex("-$epoch", targetStr.indexOf("@") - 1)
              }
          else (it.key to it.value)
        }
        .toMap()
    overriddenMap.forEach(::println)
  }

  private fun String.insertStringAtIndex(stringToBeInserted: String, indexToInsert: Int): String {
    var newString = ""
    for (i in indices) {
      newString += this[i]
      if (i == indexToInsert) {
        newString += stringToBeInserted
      }
    }
    return newString
  }
}

@JsonClass(generateAdapter = true) internal data class Environment(val values: List<EnvValue>)

@JsonClass(generateAdapter = true)
internal data class EnvValue(val key: String, val value: String?, val enabled: Boolean)
