package ga.overfullstack.contextdef.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextSObjectHydrationInfo(val queryAttribute: String, val sObjectDomain: String)
