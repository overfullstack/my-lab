package context

import com.github.underscore.U
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.io.IOAccess
import postman.PostmanAPI

val pm by lazy { PostmanAPI() }

internal val jsContext: Context by lazy {
  buildJSContext(false).also {
    it.getBindings("js").putMember("pm", pm)
    it.getBindings("js").putMember("xml2Json", xml2Json)
  }
}

fun buildJSContext(useCommonjsRequire: Boolean = true): Context {
  val options = buildMap {
    if (useCommonjsRequire) {
      put("js.commonjs-require", "true")
      put("js.commonjs-require-cwd", ".")
      put("js.commonjs-core-modules-replacements", "path:path-browserify")
    }
    put("js.esm-eval-returns-exports", "true")
    put("engine.WarnInterpreterOnly", "false")
  }
  return Context.newBuilder("js")
    .allowExperimentalOptions(true)
    .allowIO(IOAccess.ALL)
    .options(options)
    .allowHostAccess(HostAccess.ALL)
    .allowHostClassLookup { true }
    .build()
}

@OptIn(ExperimentalStdlibApi::class)
@JvmField
val xml2Json = Xml2Json { xml ->
  Moshi.Builder().build().adapter<Map<String, Any>>().fromJson(U.xmlToJson(xml))
}

@SuppressWarnings("kotlin:S6517")
@FunctionalInterface // DON'T REMOVE THIS. Polyglot won't work without this
fun interface Xml2Json {
  @Suppress("unused")
  fun xml2Json(xml: String): Map<String, Any>?
}
