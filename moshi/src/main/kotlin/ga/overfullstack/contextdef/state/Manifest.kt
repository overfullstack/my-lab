package ga.overfullstack.contextdef.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Manifest(
  val description: String,
  val label: String,
  val name: String,
  val version: String
)
