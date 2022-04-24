import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess

fun buildContext(): Context {
  val options = mutableMapOf(
    "js.commonjs-require" to "true",
    "js.commonjs-require-cwd" to "graal-js",
    "js.esm-eval-returns-exports" to "true",
    "js.commonjs-core-modules-replacements" to "buffer:buffer/, path:path-browserify",
  )
  return Context.newBuilder("js")
    .allowExperimentalOptions(true)
    .allowIO(true)
    .options(options)
    .allowHostAccess(HostAccess.ALL)
    .allowHostClassLookup { true }
    .build()
}
