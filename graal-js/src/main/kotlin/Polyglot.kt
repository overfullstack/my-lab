import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Source

class State {
  var x: Int = 0
    @HostAccess.Export set

  @HostAccess.Export
  fun callback(data: Data) {
    println(data)
  }
}

class PostmanAPI {
  @JvmField
  val environment: PostmanEnvironment = PostmanEnvironment()
  lateinit var request: Request

  lateinit var response: Response
}

data class PostmanEnvironment(private val environment: MutableMap<String, String> = mutableMapOf()) :
  MutableMap<String, String> by environment {
  fun set(key: String, value: String) {
    environment[key] = value
  }
}

data class Data(val x: Int, val y: Int)

fun main() {
  // val callingScript = "state.callback({x: 1, y: 2})"
  val callingScript = "pm.environment.set('x', '1'); pm.environment.set('y', '2'); console.log(pm.environment);"
  val context = buildContext()
  val source = Source.newBuilder("js", callingScript, "myScript.js").build()
  // val state = State()
  val pm = PostmanAPI()
  context.getBindings("js").putMember("pm", pm)
  context.eval(source)
  println(pm.environment)
}

