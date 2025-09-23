import com.diffplug.spotless.LineEnding.PLATFORM_NATIVE
import io.gitlab.arturbosch.detekt.Detekt

plugins {
  application
  id("com.diffplug.spotless")
  id("org.jetbrains.kotlinx.kover")
  id("io.gitlab.arturbosch.detekt")
  id("com.autonomousapps.dependency-analysis")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

java { toolchain { languageVersion.set(JavaLanguageVersion.of(libs.jdk.toString())) } }

tasks.withType<JavaCompile>().configureEach {
  options.compilerArgs.add("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
  jvmArgs("--enable-preview")
}


spotless {
  lineEndings = PLATFORM_NATIVE
  kotlin {
    target("src/*/kotlin/**/*.kt", "src/*/java/**/*.kt")
    targetExclude("build/**", ".gradle/**", "generated/**", "**/bin/**", "out/**", "tmp/**")
    ktfmt().googleStyle()
    trimTrailingWhitespace()
    endWithNewline()
  }
  kotlinGradle {
    target("*.gradle.kts", "src/**/*.gradle.kts")
    targetExclude("build/**", ".gradle/**", "generated/**", "**/bin/**", "out/**", "tmp/**")
    ktfmt().googleStyle()
    trimTrailingWhitespace()
    endWithNewline()
  }
  java {
    target("src/*/java/**/*.java")
    targetExclude("build/**", ".gradle/**", "generated/**", "**/bin/**", "out/**", "tmp/**")
    googleJavaFormat()
    importOrder()
    removeUnusedImports()
    removeWildcardImports()
    trimTrailingWhitespace()
    leadingSpacesToTabs(2)
    endWithNewline()
  }
  format("documentation") {
    target("*.md", "*.adoc")
    trimTrailingWhitespace()
    leadingSpacesToTabs(2)
    endWithNewline()
  }
}

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

tasks.withType<Detekt>().configureEach {
  jvmTarget = "22"
}
