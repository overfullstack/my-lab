plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.ksp)
}
dependencies {
  implementation(libs.bundles.arrow)

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.guava)
  implementation(libs.apache.commons.lang3)
  implementation(libs.bundles.kotest)
}
