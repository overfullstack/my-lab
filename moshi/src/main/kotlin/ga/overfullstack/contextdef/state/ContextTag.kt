package ga.overfullstack.contextdef.state


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextTag(
  val name: String
)
