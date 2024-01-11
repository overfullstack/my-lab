package ga.overfullstack.adapter

import com.squareup.moshi.FromJson

val postManVariableRegex = "\\{\\{(?<variableKey>[^{}]*?)}}".toRegex()

object RegexAdapter {
  @FromJson
  fun fromJson(value: String): String =
    // * NOTE 11/01/24 gopala.akshintala: This gets called for every JSON key and value
    postManVariableRegex.replace(value) { matchResult -> matchResult.value.uppercase() }
}
