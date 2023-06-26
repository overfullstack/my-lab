package postman

import com.squareup.moshi.JsonClass
import postman.state.Request

class PostmanAPI {
  @JvmField
  val environment: PostmanEnvironment = PostmanEnvironment()
  lateinit var request: Request
  lateinit var response: Response

  @Suppress("unused")
  fun setEnvironmentVariable(key: String, value: String) {
    environment.set(key, value)
  }
}

@JsonClass(generateAdapter = true)
data class Response(val code: String, val status: String, val body: String)
