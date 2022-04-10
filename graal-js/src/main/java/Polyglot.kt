import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Source

class State {
  @set:HostAccess.Export
  var x: Int = 0
  
  @HostAccess.Export
  fun callback(data: Data) {
    println(data)
  }
}

data class Data(val x: Int, val y: Int)
fun main() {
  accessJavaObjectFromScript()
}

private fun accessJavaObjectFromScript() {
  val callingScript = "state.callback({x: 1, y: 2})"
  Context.newBuilder("js")
    .option("js.ecmascript-version", "2021").build().use { context ->
      val source = Source.newBuilder("js", callingScript, "src.js").build()
      val state = State()
      context.getBindings("js").putMember("state", state)
      context.eval(source)
    }
}
