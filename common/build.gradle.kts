plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
}
dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(libs.moshi)
  implementation(libs.moshi.kotlin)
  implementation(libs.moshix.adapters)
}
moshi {
  enableSealed by true
  generateProguardRules by false
}
