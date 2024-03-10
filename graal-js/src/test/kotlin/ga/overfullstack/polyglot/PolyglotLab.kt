package ga.overfullstack.polyglot

import context.buildJSContext
import ga.overfullstack.polyglot.PolyglotLab.UpperCase
import com.salesforce.revoman.input.readFileInResourcesToString
import java.util.Locale
import org.graalvm.polyglot.Source
import org.junit.jupiter.api.Test
import postman.PostmanAPI

class PolyglotLab {

  @Test
  fun jsonParseWithJs() {
    val responseBody = readFileInResourcesToString("core-user-creation-response.json")
    val context = buildJSContext(useCommonjsRequire = false)
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
    val pm = PostmanAPI()
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
    val context = buildJSContext(useCommonjsRequire = false)
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanAPI()
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
    val context = buildJSContext(useCommonjsRequire = false)
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanAPI()
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
