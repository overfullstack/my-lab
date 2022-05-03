package ga.overfullstack.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bean(val name: String, val items: List<String>)

@JsonClass(generateAdapter = true)
data class NestedBean(val name: String, val bean: Bean)
