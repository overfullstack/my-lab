package withRequireJs

import context.buildJSContext
import org.graalvm.polyglot.Context

fun main() {
  val cx: Context = buildJSContext()
  cx.eval("js", "const newman = require('postman-sandbox');")
}

