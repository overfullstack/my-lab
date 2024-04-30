plugins {
  application
  id("org.jetbrains.kotlinx.kover")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

java { toolchain { languageVersion.set(JavaLanguageVersion.of(libs.jdk.toString())) } }

testing {
  suites {
    val test by getting(JvmTestSuite::class) { useJUnitJupiter(libs.junitVersion.toString()) }
  }
}
