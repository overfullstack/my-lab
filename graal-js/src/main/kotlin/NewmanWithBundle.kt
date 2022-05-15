import org.graalvm.polyglot.Source
import java.io.File
import java.io.IOException

fun main() {
  try {
    readAndEvaluateJavaScriptSource()
  } catch (e: IOException) {
    e.printStackTrace()
  }
}

@Throws(IOException::class)
private fun readAndEvaluateJavaScriptSource() {
  val jsFile = File("graal-js/src/withbundle.withbundle.withbundle.main/resources/newman.js")
  // load output from WebPack for Validator Module - a single bundled JS file
  val context = buildContext()
  context.eval(Source.newBuilder("js", jsFile).build())
  println("All functions available: ${context.getBindings("js").memberKeys}")
}
