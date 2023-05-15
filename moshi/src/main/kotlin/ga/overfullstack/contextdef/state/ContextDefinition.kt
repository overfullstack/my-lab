package ga.overfullstack.contextdef.state


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextDefinition(
  val manifest: Manifest,
  val payload: Payload
)
