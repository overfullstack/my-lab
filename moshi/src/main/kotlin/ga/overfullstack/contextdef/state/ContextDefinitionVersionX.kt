package ga.overfullstack.contextdef.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextDefinitionVersionX(
  val versionNumber: String,
  val isActive: String,
  val startDate: String,
  val contextNodes: List<ContextNode>,
  val contextMapping: List<ContextMapping>?,
)
