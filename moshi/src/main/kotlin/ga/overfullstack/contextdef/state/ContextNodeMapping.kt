package ga.overfullstack.contextdef.state

import arrow.optics.optics
import com.squareup.moshi.JsonClass

@optics
@JsonClass(generateAdapter = true)
data class ContextNodeMapping(
  val sObjectName: String,
  val contextNodeId: String,
  val contextAttributeMappingList: List<ContextAttributeMapping>,
) {
  companion object
}
