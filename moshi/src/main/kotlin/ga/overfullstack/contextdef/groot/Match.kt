package ga.overfullstack.contextdef.groot

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Match(val contextNodeAttributeName: String, val queryAttribute: String)
