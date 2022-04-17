fun main() {
  val postManVariableRegex = "\\{\\{([^}]+)}}".toRegex()
  
  postManVariableRegex.replace("{{abc}}") { matchResult: MatchResult -> 
    println(matchResult.groupValues) 
    matchResult.groupValues[0] }
}
