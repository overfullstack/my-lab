package withRequireJs

import buildContext

fun main() {
  val context = buildContext()
  context.eval("js", "const validator = require('validator'); console.info(validator.isEmail('foo'));")
}

