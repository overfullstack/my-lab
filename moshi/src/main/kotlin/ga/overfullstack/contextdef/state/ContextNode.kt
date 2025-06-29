package ga.overfullstack.contextdef.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextNode(
  val contextNodeId: String,
  val name: String,
  val nodeAttributes: List<NodeAttribute>,
)
