import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA_PARALLEL
import com.diffplug.spotless.LineEnding.PLATFORM_NATIVE

plugins {
  java
  id("com.diffplug.spotless")
  id("com.adarshr.test-logger")
  id("io.gitlab.arturbosch.detekt")
  id("com.github.spotbugs") apply false
}

version = "1.0.0"

group = "ga.overfullstack"

description = "My Lab"

spotless {
  lineEndings = PLATFORM_NATIVE
  kotlin {
    ktfmt().googleStyle()
    target("src/*/kotlin/**/*.kt", "src/*/java/**/*.kt")
    trimTrailingWhitespace()
    endWithNewline()
    targetExclude("build/**", ".gradle/**", "generated/**", "bin/**", "out/**", "tmp/**")
  }
  kotlinGradle {
    ktfmt().googleStyle()
    trimTrailingWhitespace()
    endWithNewline()
    targetExclude("build/**", ".gradle/**", "generated/**", "bin/**", "out/**", "tmp/**")
  }
  java {
    toggleOffOn()
    target("src/*/java/**/*.java")
    importOrder()
    removeUnusedImports()
    googleJavaFormat()
    trimTrailingWhitespace()
    indentWithSpaces(2)
    endWithNewline()
    targetExclude("build/**", ".gradle/**", "generated/**", "bin/**", "out/**", "tmp/**")
  }
  format("documentation") {
    target("*.md", "*.adoc")
    trimTrailingWhitespace()
    indentWithSpaces(2)
    endWithNewline()
  }
}

detekt {
  parallel = true
  buildUponDefaultConfig = true
  baseline = file("$rootDir/detekt/baseline.xml")
  config.setFrom(file("$rootDir/detekt/config.yml"))
}

testlogger {
  theme = MOCHA_PARALLEL
  showCauses = false
  showSimpleNames = true
}

tasks {
  spotbugsMain.get().enabled = false
  spotbugsTest.get().enabled = false
  spotbugs.ignoreFailures.set(true)
}
