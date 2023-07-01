package ga.overfullstack.contextdef.sobject

object Udd {
  private val fieldMap = mapOf("Quote" to listOf("AccountId", "Pricebook2Id", "Address"))

  fun getFieldsForAPIName(apiName: String): List<String>? = fieldMap[apiName]
}
