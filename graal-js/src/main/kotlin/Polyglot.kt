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
  val responseBody = """
    {
      "x": "1",
      "y": [
        "2"
      ]
    }
  """.trimIndent()
  val callingScript = """
    var jsonData=JSON.parse(responseBody);
    console.log(jsonData);
    pm.environment.set('x', jsonData.x); 
    pm.environment.set('y', jsonData.y[0]);
    console.log(pm.environment);
    
  """.trimIndent()
  val context = buildContext(useRequireJs = false)
  val source = Source.newBuilder("js", callingScript, "myScript.js").build()
  // val state = State()
  val pm = PostmanAPI()
  val jsBindings = context.getBindings("js")
  jsBindings.putMember("pm", pm)
  jsBindings.putMember("responseBody", responseBody)
  context.eval(source)
  println(pm.environment)
}

