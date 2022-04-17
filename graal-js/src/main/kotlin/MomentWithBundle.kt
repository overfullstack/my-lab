import org.graalvm.polyglot.Source
import java.io.File

fun main() {
  val momentJs = File("graal-js/src/main/resources/moment.js")
  val language = Source.findLanguage(momentJs)
  val context = buildContext()
  val exports = context.eval(Source.newBuilder(language, momentJs).mimeType("application/javascript+module").build())
  println(exports.getMember("default"))
}
