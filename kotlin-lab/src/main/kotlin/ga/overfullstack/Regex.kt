package ga.overfullstack

fun main() {
  val regex = "\\{\\{([^}]+)}}".toRegex()
  val strWithRegex = "{{abc}} - {{pqr}}"
  val replacement = mapOf("abc" to "123", "pqr" to "456")
  val replacedStr =
    regex.replace(strWithRegex) { matchResult: MatchResult ->
      replacement[matchResult.groupValues[1]] ?: ""
    }
  println(replacedStr)
}
