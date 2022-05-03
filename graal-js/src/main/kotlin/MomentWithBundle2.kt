import org.graalvm.polyglot.Source
import java.io.File

fun main() {
  val momentJs = File("graal-js/src/main/resources/revoman-root.js")
  val language = Source.findLanguage(momentJs)
  val context = buildContext()
  val exports = context.eval(Source.newBuilder(language, momentJs).mimeType("application/javascript+module").build())
  val moment = exports.getMember("default")
  println(moment.execute().invokeMember("format", "dddd"))
}
