package withRequireJs

import context.buildJSContext

fun main() {
  val context = buildJSContext()
  context.eval(
    "js",
    """
     var moment = require('moment');
     var scheduleTime = moment().add(62, 'seconds').format("HH:mm:ss.SSS")
     console.log(scheduleTime)
  """
      .trimIndent()
  )
}
