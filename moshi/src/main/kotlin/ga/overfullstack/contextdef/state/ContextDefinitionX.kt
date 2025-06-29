package ga.overfullstack.contextdef.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextDefinitionX(
  val name: String,
  val description: String,
  val developerName: String,
  val contextDefinitionVersionList: List<ContextDefinitionVersion>,
)
