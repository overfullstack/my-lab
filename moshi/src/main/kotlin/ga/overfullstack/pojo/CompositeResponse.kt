package ga.overfullstack.pojo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompositeResponse(
  val compositeResponse: List<CompositeResponseX>
)

@JsonClass(generateAdapter = true)
data class CompositeResponseX(
    val body: Body,
    val httpHeaders: HttpHeaders,
    val httpStatusCode: Int,
    val referenceId: String
)

@JsonClass(generateAdapter = true)
data class Body(
  val done: Boolean,
  val records: List<Map<String, Any>>,
  val totalSize: Int
)

@JsonClass(generateAdapter = true)
data class Record(
    val attributes: Attributes,
    val recordBody: Map<String, String>
)

@JsonClass(generateAdapter = true)
class HttpHeaders

@JsonClass(generateAdapter = true)
data class Attributes(
  val type: String,
  val url: String
)
