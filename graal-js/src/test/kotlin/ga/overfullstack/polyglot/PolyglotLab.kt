package ga.overfullstack.polyglot

import com.salesforce.revoman.input.readFileInResourcesToString
import context.buildJSContext
import ga.overfullstack.polyglot.PolyglotLab.UpperCase
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.graalvm.polyglot.Source
import org.junit.jupiter.api.Test
import postman.PostmanSDK
import java.util.*

class PolyglotLab {
  private val context = buildJSContext()

  @Test
  fun `Function as Value`() {
    val context = buildJSContext(null)
    val jscript = """
      jsonStr => JSON.parse(jsonStr)
    """
    val jsonStr = readFileInResourcesToString("core-graph-response.json")
    val result = context.eval("js", jscript).execute(jsonStr)
    println(result)
  }

  @Test
  fun `use moment from bundle`() {
    val momentJs = FileSystem.RESOURCES.source("jsbundle/moment.js".toPath()).buffer().readUtf8()
    val context = buildJSContext()
    val jscript =
      """
     var moment = require('moment');
     var scheduleTime = moment().add(62, 'seconds').format("HH:mm:ss.SSS")
     console.log(scheduleTime)
  """
    context.eval(
      Source.newBuilder("js", momentJs, "moment")
        .content(jscript)
        .mimeType("application/javascript+module")
        .build()
    )
  }

  @Test
  fun `use moment from node_modules`() {
    val nodeModulePath = javaClass.classLoader.getResource("node_modules")?.path
    val context2 = buildJSContext(nodeModulesRelativePath = nodeModulePath.toString())
    val jscript =
      """
     var moment = require('moment');
     var scheduleTime = moment().add(62, 'seconds').format("HH:mm:ss.SSS")
     console.log(scheduleTime)
     """
        .trimIndent()
    context2.eval(
      Source.newBuilder("js", jscript, "jscript").mimeType("application/javascript+module").build(),
    )
  }

  @Test
  fun `use moment with revoman`() {
    val jscript =
      """
     var moment = require('moment');
     var scheduleTime = moment().add(62, 'seconds').format("HH:mm:ss.SSS")
     console.log(scheduleTime)
     """
        .trimIndent()
    // evaluateJS(jscript)
  }

  @Test
  fun jsonParseWithJs() {
    val responseBody = readFileInResourcesToString("core-user-creation-response.json")
    val callingScript =
      """
      var jsonData = JSON.parse(responseBody);
      var statusCode = jsonData.compositeResponse[0].httpStatusCode;
      if (statusCode === 201) {
          jsonData.compositeResponse.forEach(function(response) {
              var referenceId = response.referenceId;
              if(referenceId === "refOrder") {
                  pm.environment.set("orderId", response.body.id);
              } else if(response.referenceId === "refOnetimeOrderItem") {
                  pm.environment.set("oneTimeOrderItemId", response.body.id);
              } else if(response.referenceId === "refEvergreenOrderItem") {
                  pm.environment.set("evergreenOrderItemId", response.body.id);
              } else if(response.referenceId === "refTermedOrderItem") {
                  pm.environment.set("termedOrderItemId", response.body.id);
              }
          });
      }
    """
        .trimIndent()
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanSDK()
    val jsBindings = context.getBindings("js")
    jsBindings.putMember("postman", pm)
    jsBindings.putMember("responseBody", responseBody)
    context.eval(source)
    println(pm.environment)
  }

  @Test
  fun `set object inside pm env`() {
    val responseBody =
      """
    {
      "x": "a",
      "y": [
        "2"
      ]
    }
    """
        .trimIndent()
    val callingScript =
      """
      var jsonData=JSON.parse(responseBody);
      console.log(jsonData);
      pm.environment.set('jsonData', jsonData);
    """
        .trimIndent()
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanSDK()
    val jsBindings = context.getBindings("js")
    jsBindings.putMember("pm", pm)
    jsBindings.putMember("responseBody", responseBody)
    context.eval(source)
    println(pm.environment)
    ((pm.environment["jsonData"] as Map<String, String>)["y"] as List<String>).forEach(::println)
  }

  @Test
  fun jsonParseWithCallback() {
    val responseBody =
      """
    {
      "x": "a",
      "y": [
        "2"
      ]
    }
    """
        .trimIndent()
    val callingScript =
      """
      var jsonData=JSON.parse(responseBody);
      console.log(jsonData);
      pm.environment.set('x', toUpperCase(jsonData.x));
      pm.environment.set('y', jsonData.y);
    """
        .trimIndent()
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanSDK()
    val toUpperCase = UpperCase { s -> s.uppercase(Locale.getDefault()) }
    val jsBindings = context.getBindings("js")
    jsBindings.putMember("pm", pm)
    jsBindings.putMember("responseBody", responseBody)
    jsBindings.putMember("toUpperCase", toUpperCase)
    context.eval(source)
    println(pm.environment)
  }

  @FunctionalInterface
  fun interface UpperCase {

    @Suppress("unused") fun toUpperCase(s: String): String
  }
}
