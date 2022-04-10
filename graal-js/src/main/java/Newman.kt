import org.graalvm.polyglot.Context

fun main() {
  val options: MutableMap<String, String> = HashMap()
  options["js.commonjs-require"] = "true"
  options["js.commonjs-require-cwd"] = "graal-js"
  //options["js.commonjs-global-properties"] = "./globals.js"
  options["js.commonjs-core-modules-replacements"] =
    "buffer:buffer/, path:path-browserify, crypto:crypto-js, uuid:uuid-browser"
  val cx: Context = Context.newBuilder("js")
    .allowExperimentalOptions(true)
    .allowIO(true)
    .options(options)
    .build()

  cx.eval("js", "const newman = require('newman');")
}

