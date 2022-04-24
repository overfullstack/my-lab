fun main() {
  val context = buildContext()
  context.eval(
    "js", """
     var moment = require('moment');
     var scheduleTime = moment().add(62, 'seconds').format("HH:mm:ss.SSS")
     console.log(scheduleTime)
  """.trimIndent()
  )
}
