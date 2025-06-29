package withRequireJs

import context.buildJSContext

fun main() {
  val context = buildJSContext()
  context.eval(
    "js",
    "const validator = require('validator'); console.info(validator.isEmail('foo'));",
  )
}
