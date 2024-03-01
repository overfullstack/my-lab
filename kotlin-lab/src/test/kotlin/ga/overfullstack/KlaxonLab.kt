package ga.overfullstack

import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import org.junit.jupiter.api.Test
import java.io.StringReader
import java.util.regex.Pattern

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
      .parseJsonObject(StringReader(readFileInResourcesToString("json/library.json")))
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
      .parseJsonObject(
        StringReader(readFileInResourcesToString("json/pokemon.postman_collection.json"))
      )
  }
}
