package ga.overfullstack.pojo.composite

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GraphResponse(val graphs: List<Graph>) {
  sealed interface Graph {
    @JsonClass(generateAdapter = true)
    data class SuccessGraph(
      val graphId: String,
      val graphResponse: GraphResponse,
      val isSuccessful: Boolean
    ) : Graph {
      @JsonClass(generateAdapter = true)
      data class GraphResponse(val compositeResponse: List<CompositeResponse>) {
        @JsonClass(generateAdapter = true)
        data class CompositeResponse(
          val body: Body,
          val httpHeaders: HttpHeaders,
          val httpStatusCode: Int,
          val referenceId: String
        ) {
          @JsonClass(generateAdapter = true)
          data class Body(val errors: List<Any>, val id: String, val success: Boolean)

          @JsonClass(generateAdapter = true)
          data class HttpHeaders(@Json(name = "Location") val location: String)
        }
      }
    }

    @JsonClass(generateAdapter = true)
    data class ErrorGraph(
      val graphId: String,
      val graphResponse: GraphResponse,
      val isSuccessful: Boolean
    ) : Graph {
      @JsonClass(generateAdapter = true)
      data class GraphResponse(val compositeResponse: List<CompositeResponse>) {
        @JsonClass(generateAdapter = true)
        data class CompositeResponse(
          val body: List<Body>,
          val httpHeaders: HttpHeaders,
          val httpStatusCode: Int,
          val referenceId: String
        ) {
          @JsonClass(generateAdapter = true)
          data class Body(val errorCode: String, val fields: List<Any>?, val message: String)

          @JsonClass(generateAdapter = true) class HttpHeaders
        }
      }
    }
  }
}
