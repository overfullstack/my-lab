import io.vavr.Function2
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import java.io.File
import java.io.IOException

fun main() {
  try {
    readAndEvaluateJavaScriptSource()
    println(isPostalCode("3214 TT", "NL"))
  } catch (e: IOException) {
    e.printStackTrace()
  }
}

private val context = Context.newBuilder("js").build()
  //.option("js.ecmascript-version", "2021").build()

@Throws(IOException::class)
private fun readAndEvaluateJavaScriptSource() {
  val jsFile = File("graal-js/src/main/resources/validator_bundle.js")
  // load output from WebPack for Validator Module - a single bundled JS file
  context.eval(Source.newBuilder("js", jsFile).build())
  println("All functions available from Java (as loaded into Bindings) ${context.getBindings("js").memberKeys}")
}

fun isPostalCode(postalCodeToValidate: String, country: String): Boolean {
  val postalCodeValidator: Function2<String, String, Boolean> =
    context.getBindings("js").getMember("isPostalCode").`as`(io.vavr.Function2::class.java) as Function2<String, String, Boolean>
  return postalCodeValidator.apply(postalCodeToValidate, country)
}
