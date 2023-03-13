plugins {
  id("mylab.kt-conventions")
}
dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
kotlin {
  sourceSets.all {
    languageSettings {
      languageVersion = "1.8"
    }
  }
}
