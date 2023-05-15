package ga.overfullstack.contextdef.groot


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapperConfig(
  val match: List<Match>,
  val exclude: Set<String>,
  val sObjectName: String,
  val sObjectDomain: String,
  val contextMappingName: String,
)
