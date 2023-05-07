package ga.overfullstack.state

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bean(val name: String, val items: List<String>)

@JsonClass(generateAdapter = true)
data class NestedBean(val name: String, val bean: Bean)

@JsonClass(generateAdapter = true)
data class Obj(val a: String, val b: String, val c: String)

@JsonClass(generateAdapter = true)
@JvmInline
value class Message(val message: String)
