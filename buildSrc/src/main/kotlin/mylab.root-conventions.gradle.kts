import com.adarshr.gradle.testlogger.theme.ThemeType
import com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep.XML
import kotlinx.kover.api.DefaultJacocoEngine
import kotlinx.kover.api.KoverTaskExtension

plugins {
  java
  idea
  id("com.diffplug.spotless")
  id("org.jetbrains.kotlinx.kover")
  id("com.adarshr.test-logger")
  id("io.gitlab.arturbosch.detekt")
  id("com.github.spotbugs") apply false
}

version = "1.0.0"
group = "ga.overfullstack"
description = "My Lab"
repositories {
  mavenCentral()
}
kover {
  isDisabled.set(false)
  engine.set(DefaultJacocoEngine)
}
koverMerged {
  enable()
  xmlReport {
    onCheck.set(true)
  }
}
spotless {
  kotlin {
    target("src/main/java/**/*.kt", "src/test/java/**/*.kt")
    targetExclude("$buildDir/generated/**/*.*")
    ktlint()
      .setUseExperimental(true)
      .editorConfigOverride(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktlint()
      .setUseExperimental(true)
      .editorConfigOverride(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
  }
  java {
    toggleOffOn()
    target("src/main/java/**/*.java", "src/test/java/**/*.java")
    targetExclude("$buildDir/generated/**/*.*")
    importOrder()
    removeUnusedImports()
    googleJavaFormat()
    trimTrailingWhitespace()
    indentWithSpaces(2)
    endWithNewline()
  }
  format("xml") {
    targetExclude("pom.xml")
    target("*.xml")
    eclipseWtp(XML)
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
  config = files("$rootDir/detekt/config.yml")
}
tasks {
  spotbugsMain.get().enabled = false
  spotbugsTest.get().enabled = false
  spotbugs.ignoreFailures.set(true)
  test {
    extensions.configure(KoverTaskExtension::class) {
      isEnabled = true
    }
  }
  testlogger.theme = ThemeType.MOCHA
}
