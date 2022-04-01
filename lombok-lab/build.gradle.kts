plugins {
  id("io.freefair.lombok")
  id("com.github.spotbugs") version "4.8.0"
}

tasks {
  spotbugs.ignoreFailures.set(true)
  spotbugsTest.get().enabled = false
}
