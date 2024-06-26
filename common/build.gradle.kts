plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
}

dependencies {
  // implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(libs.moshi.kotlin)
  implementation(libs.moshix.adapters)
  testImplementation(libs.assertj.core)
  implementation(libs.okio.jvm)
}

moshi { enableSealed = true }
