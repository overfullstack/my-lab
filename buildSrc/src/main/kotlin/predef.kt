import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

infix fun <T> Property<T>.by(value: T) {
  set(value)
}

internal val VersionCatalog.kotestBundle: Provider<ExternalModuleDependencyBundle>
  get() = getBundle("kotest")

internal val VersionCatalog.testLogger
  get() = getPlugin("testLogger")

internal val VersionCatalog.kotlinJVM
  get() = getPlugin("kotlin-jvm")

internal val VersionCatalog.junitBom
  get() = getLibrary("junit-bom")

internal val VersionCatalog.junitBundle
  get() = getBundle("junit")

internal val VersionCatalog.jdk
  get() = getVersion("jdk")

internal val VersionCatalog.junitVersion
  get() = getVersion("junit")

private fun VersionCatalog.getLibrary(library: String) = findLibrary(library).get()

private fun VersionCatalog.getBundle(bundle: String) = findBundle(bundle).get()

private fun VersionCatalog.getPlugin(plugin: String) = findPlugin(plugin).get()

private fun VersionCatalog.getVersion(plugin: String) = findVersion(plugin).get()
