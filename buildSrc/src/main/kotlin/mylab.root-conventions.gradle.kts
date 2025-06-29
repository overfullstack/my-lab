import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA_PARALLEL
import com.diffplug.spotless.LineEnding.PLATFORM_NATIVE

plugins {
  java
  id("com.adarshr.test-logger")
  id("com.github.spotbugs") apply false
}

version = "1.0.0"

group = "ga.overfullstack"

description = "My Lab"

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
