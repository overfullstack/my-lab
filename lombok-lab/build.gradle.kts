plugins {
  id("io.freefair.lombok")
  id("com.github.spotbugs") version "5.0.9"
}

tasks {
  spotbugs.ignoreFailures.set(true)
  spotbugsTest.get().enabled = false
}
