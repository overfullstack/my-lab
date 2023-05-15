package ga.overfullstack.contextdef.state


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NodeAttribute(
  val contextAttributeId: String,
  val contextTagList: List<ContextTag>,
  val dataType: String,
  val name: String,
  val type: String
)
