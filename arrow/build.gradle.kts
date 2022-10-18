@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.ksp)
}

dependencies {
  implementation(platform("io.arrow-kt:arrow-stack:1.1.4-alpha.8"))
  implementation("io.arrow-kt:arrow-core")
  implementation("io.arrow-kt:arrow-fx-coroutines")
  implementation("io.arrow-kt:arrow-optics")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  implementation("com.google.guava:guava:31.1-jre")
  implementation(libs.apache.commons.lang3)
  implementation(libs.bundles.kotest)
}