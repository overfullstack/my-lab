package ga.overfullstack

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Bean(val name: String, val items: List<String>)

@JsonClass(generateAdapter = true)
internal data class NestedBean(val name: String, val bean: Bean)
