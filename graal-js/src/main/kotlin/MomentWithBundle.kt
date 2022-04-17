import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import java.io.File

fun main() {
  val momentJs = File("graal-js/src/main/resources/moment.js")
  val language = Source.findLanguage(momentJs)
  val context = Context.newBuilder("js").allowIO(true).build()
  context.eval(Source.newBuilder(language, momentJs).mimeType("application/javascript+module").build())
  println("All functions available: ${context.getBindings(language).memberKeys}")
}
