package ga.overfullstack

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.zacsweers.moshix.sealed.annotations.DefaultObject
import dev.zacsweers.moshix.sealed.annotations.TypeLabel
import org.junit.jupiter.api.Test

class SealedLab {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun sealed() {
    val successStr = readFileToString("sealed/success.json")
    // val errorStr = readFileToString("sealed/error.json")
    val sObject = Moshi.Builder().build().adapter<SObject>().fromJson(successStr)
    println(sObject)
  }

  @JsonClass(generateAdapter = true, generator = "sealed:success")
  internal sealed class SObject {

    @TypeLabel("true")
    @JsonClass(generateAdapter = true)
    data class Success(val id: String, val success: Boolean, val errors: List<String>) : SObject()

    @TypeLabel("")
    @JsonClass(generateAdapter = true)
    data class Error(val errors: List<SObjectError>) : SObject()

    @DefaultObject object Unknown : SObject()
  }

  @JsonClass(generateAdapter = true)
  internal data class SObjectError(
    val message: String,
    val errorCode: String,
    val fields: List<String>?,
  )
}
