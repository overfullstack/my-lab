package withbundle

import context.buildJSContext
import java.io.File
import org.graalvm.polyglot.Source

fun main() {
  val momentJs = File("graal-js/src/withbundle/main/resources/moment.js")
  val language = Source.findLanguage(momentJs)
  val context = buildJSContext()
  val exports =
    context.eval(
      Source.newBuilder(language, momentJs).mimeType("application/javascript+module").build()
    )
  val moment = exports.getMember("default")
  println(moment.execute().invokeMember("format", "dddd"))
}
