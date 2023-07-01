package withbundle

import context.buildJSContext
import java.io.File
import java.io.IOException
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source

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
  val context = buildJSContext()
  context.eval(Source.newBuilder("js", jsFile).build())
  println("All functions available: ${context.getBindings("js").memberKeys}")
}

fun readAndEvaluateWithBundle() {
  val cx: Context = buildJSContext()
  cx.eval("js", "const newman = require('newman');")
}
