package withbundle

import context.buildJSContext

fun main() {
  val context = buildJSContext()
  //  val xml2Json = File("graal-js/src/withbundle/main/resources/xml2Json.js")
  //  val language = Source.findLanguage(xml2Json)
  // val exports = context.eval(Source.newBuilder(language,
  // xml2Json).mimeType("application/javascript+module").build())

  // This path is relative to `js.commonjs-require-cwd` where node_modules is located.
  context.eval(
    "js",
    "const xml2Json = require('graal-js/src/main/resources/xml2Json.js'); console.info(xml2Json('<a>1</a>')['a']);",
  )
}
