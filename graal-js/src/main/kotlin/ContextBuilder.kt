import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess

fun buildContext(): Context {
  val options: MutableMap<String, String> = mutableMapOf()
  options["js.commonjs-require"] = "true"
  options["js.commonjs-require-cwd"] = "graal-js"
  options["js.commonjs-core-modules-replacements"] =
    "buffer:buffer/, path:path-browserify"
  return Context.newBuilder("js")
    .allowExperimentalOptions(true)
    .allowIO(true)
    .options(options)
    .allowHostAccess(HostAccess.ALL)
    .allowHostClassLookup { true }
    .build()
}
