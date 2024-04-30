plugins {
  application
  id("org.jetbrains.kotlinx.kover")
  id("io.gitlab.arturbosch.detekt")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

java { toolchain { languageVersion.set(JavaLanguageVersion.of(libs.jdk.toString())) } }

testing {
  suites {
    val test by getting(JvmTestSuite::class) { useJUnitJupiter(libs.junitVersion.toString()) }
  }
}

detekt {
  parallel = true
  buildUponDefaultConfig = true
  baseline = file("$rootDir/detekt/baseline.xml")
  config.setFrom(file("$rootDir/detekt/config.yml"))
  ignoreFailures = true
}
