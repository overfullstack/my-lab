package ga.overfullstack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.beust.klaxon.PathMatcher
import com.salesforce.revoman.input.bufferFile
import com.salesforce.revoman.input.readFileToString
import java.io.StringReader
import java.util.regex.Pattern
import org.junit.jupiter.api.Test

class KlaxonLab {
  @Test
  fun `klaxon Json Path Query`() {
    val pathMatcher =
      object : PathMatcher {
        override fun pathMatches(path: String) = Pattern.matches(".*library.*books.*author.*", path)

        override fun onMatch(path: String, value: Any) {
          println("Adding $path = $value")
        }
      }

    Klaxon()
      .pathMatcher(pathMatcher)
      .parseJsonObject(StringReader(readFileToString("json/library.json")))
  }

  @Test
  fun `klaxon Json Path Query for collection`() {
    val pathMatcher =
      object : PathMatcher {
        override fun pathMatches(path: String) = Pattern.matches(".*item.*", path)

        override fun onMatch(path: String, value: Any) {
          println("$path = $value")
        }
      }

    Klaxon()
      .pathMatcher(pathMatcher)
      .parseJsonObject(StringReader(readFileToString("json/pokemon.postman_collection.json")))
  }

  @Test
  fun `klaxon Json Object`() {
    val parser: Parser = Parser.default()
    val stringBuilder: StringBuilder = StringBuilder("{\"name\":\"Cedric Beust\", \"age\":23}")
    val json: JsonObject = parser.parse(stringBuilder) as JsonObject
    println("Name : ${json.string("name")}, Age : ${json.int("age")}")
  }

  @Test
  fun `klaxon Json Array`() {
    val array = Parser.default().parse(bufferFile("d.json").inputStream()) as JsonArray<*>

    println("=== Finding Jack:")
    val jack = array.first { it is JsonObject && it.string("first") == "Jack" }
    println("Jack: $jack")

    println("=== Everyone who studied in Berkeley:")
    val berkeley =
      array
        .filterIsInstance<JsonObject>()
        .filter { it.obj("schoolResults")?.string("location") == "Berkeley" }
        .map { it.string("last") }
    println("${berkeley}")

    println("=== All last names:")
    val lastNames = array.string("last")
    println("$lastNames")

    println("=== All grades bigger than 75")
    val result =
      array.filterIsInstance<JsonObject>().map {
        it.obj("schoolResults")?.array<JsonObject>("scores")?.filter { it.long("grade")!! > 75 }!!
      }
    println("Result: ${result}")
  }
}
