package postman

import Request
import Response

class PostmanAPI {
  @JvmField
  val environment: PostmanEnvironment = PostmanEnvironment()
  lateinit var request: Request

  lateinit var response: Response
}

data class PostmanEnvironment(private val environment: MutableMap<String, Any> = mutableMapOf()) :
  MutableMap<String, Any> by environment {
  fun set(key: String, value: Any) {
    environment[key] = value
  }
}
