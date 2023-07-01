import com.adarshr.gradle.testlogger.theme.ThemeType
import com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep.XML

plugins {
  java
  idea
  id("com.diffplug.spotless")
  id("com.adarshr.test-logger")
  id("io.gitlab.arturbosch.detekt")
  id("com.github.spotbugs") apply false
}

version = "1.0.0"

group = "ga.overfullstack"

description = "My Lab"
// This is needed to declare repositories on each module
repositories {
  mavenCentral()
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  maven("https://repo.spring.io/milestone")
}

spotless {
  kotlin {
    ktfmt().googleStyle()
    target("**/*.kt")
    trimTrailingWhitespace()
    endWithNewline()
    targetExclude(
      "**/Dependencies.kt",
      "**/build/**",
    )
  }
  kotlinGradle {
    ktfmt().googleStyle()
    target("**/*.gradle.kts")
    trimTrailingWhitespace()
    endWithNewline()
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
  config.setFrom(file("$rootDir/detekt/config.yml"))
}

tasks {
  spotbugsMain.get().enabled = false
  spotbugsTest.get().enabled = false
  spotbugs.ignoreFailures.set(true)
  testlogger.theme = ThemeType.MOCHA
}
