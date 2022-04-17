import org.graalvm.polyglot.Source
import java.io.File

fun main() {
  val context = buildContext()
  val xml2Json = File("graal-js/src/main/resources/xml2Json.js")
  val language = Source.findLanguage(xml2Json)
  val exports = context.eval(Source.newBuilder(language, xml2Json).mimeType("application/javascript+module").build())
  println(exports.memberKeys)
}

