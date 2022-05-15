package withbundle

import io.vavr.Function2
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import java.io.File

fun main() {
  readAndEvaluateJavaScriptSource()
  println(isPostalCode("3214 TT", "NL"))
}

private val context = Context.newBuilder("js").build()

private fun readAndEvaluateJavaScriptSource() {
  val validatorBundleJs = File("graal-js/src/withbundle/main/resources/validator_bundle.js")
  context.eval(Source.newBuilder("js", validatorBundleJs).build())
  println("All functions available: ${context.getBindings("js").getMember("default").memberKeys}")
}

private fun isPostalCode(postalCodeToValidate: String, country: String): Boolean {
  val postalCodeValidator: Function2<String, String, Boolean> =
    context.getBindings("js").getMember("default").getMember("isPostalCode").`as`(io.vavr.Function2::class.java) as Function2<String, String, Boolean>
  return postalCodeValidator.apply(postalCodeToValidate, country)
}
