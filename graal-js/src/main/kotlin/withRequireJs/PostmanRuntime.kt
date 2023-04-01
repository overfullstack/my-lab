package withRequireJs

import buildContext
import org.graalvm.polyglot.Context

fun main() {
  val cx: Context = buildContext()
  cx.eval("js", "const newman = require('postman-sandbox');")
}

