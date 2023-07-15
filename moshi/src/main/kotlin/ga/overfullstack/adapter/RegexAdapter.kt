package ga.overfullstack.adapter

import com.squareup.moshi.FromJson

val postManVariableRegex = "\\{\\{(?<variableKey>[^{}]*?)}}".toRegex()

object RegexAdapter {
  @FromJson
  fun fromJson(value: String): String =
    postManVariableRegex.replace(value) { matchResult -> matchResult.value.uppercase() }
}
