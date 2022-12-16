@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("mylab.kt-conventions")
}
dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
