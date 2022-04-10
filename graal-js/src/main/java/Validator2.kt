import org.graalvm.polyglot.Context
import java.io.IOException

fun main() {
  try {
    readAndEvaluateJavaScriptSource()
  } catch (e: IOException) {
    e.printStackTrace()
  }
}

private val c = Context.create("js")

@Throws(IOException::class)
private fun readAndEvaluateJavaScriptSource() {
  val options: MutableMap<String, String> = HashMap()
// Enable CommonJS experimental support.
  options["js.commonjs-require"] = "true"
// (optional) folder where the NPM modules to be loaded are located.
  options["js.commonjs-require-cwd"] = "graal-js"
// (optional) initialization script to pre-define globals.
  //options["js.commonjs-global-properties"] = "./globals.js"
// (optional) Node.js built-in replacements as a comma separated list.
  options["js.commonjs-core-modules-replacements"] = "buffer:buffer/, path:path-browserify"
// Create context with IO support and experimental options.
  val cx: Context = Context.newBuilder("js")
    .allowExperimentalOptions(true)
    .allowIO(true)
    .options(options)
    .build()
// Require a module
// Require a module
  cx.eval("js", "const validator = require('validator'); console.info(validator.isEmail('foo'));")

  //val jsFile = File("graal-js/src/main/resources/newman.js")
  //c.eval(Source.newBuilder("js", jsFile).build())
  println("All functions available from Java (as loaded into Bindings) ${c.getBindings("js").memberKeys}")
}

