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
  val context = buildContext()
  context.eval("js", "const validator = require('validator'); console.info(validator.isEmail('foo'));")
}

