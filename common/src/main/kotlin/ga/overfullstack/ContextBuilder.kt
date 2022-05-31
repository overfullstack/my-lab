package ga.overfullstack

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess

class ContextBuilder {
  fun buildContext(): Context {
    val jarPath = javaClass
      .protectionDomain
      .codeSource
      .location
      .toURI()
      .path
    println(jarPath)
    val options = buildMap {
      put("js.commonjs-require", "true")
      put("js.commonjs-require-cwd", "$jarPath")
      put("js.commonjs-core-modules-replacements", "path:path-browserify")
      put("js.esm-eval-returns-exports", "true")
      put("engine.WarnInterpreterOnly", "false")
    }
    return Context.newBuilder("js")
      .allowExperimentalOptions(true)
      .allowIO(true)
      .options(options)
      .allowHostAccess(HostAccess.ALL)
      .allowHostClassLookup { true }
      .build()
  }
}
