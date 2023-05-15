package ga.overfullstack.contextdef.state


import arrow.optics.optics
import com.squareup.moshi.JsonClass

@optics
@JsonClass(generateAdapter = true)
data class ContextAttributeMapping(
  val contextAttributeId: String,
  val hydrationSource: String,
  val contextSObjectHydrationInfoList: List<ContextSObjectHydrationInfo>
) {
  companion object
}
