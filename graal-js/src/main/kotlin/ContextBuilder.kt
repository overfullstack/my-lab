import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess

fun buildContext(useRequireJs: Boolean = true): Context {
  val options = buildMap {
    if (useRequireJs) {
      put("js.commonjs-require", "true")
      put("js.commonjs-require-cwd", "graal-js")
      put("js.commonjs-core-modules-replacements", "buffer:buffer/, path:path-browserify")
    }
    put("js.esm-eval-returns-exports", "true")
  }
  return Context.newBuilder("js")
    .allowExperimentalOptions(true)
    .allowIO(true)
    .options(options)
    .allowHostAccess(HostAccess.ALL)
    .allowHostClassLookup { true }
    .build()
}
