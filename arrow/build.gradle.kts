plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.ksp)
}
dependencies {
  ksp(libs.arrow.optics.ksp.plugin)
  implementation(libs.bundles.arrow)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.guava)
  implementation(libs.apache.commons.lang3)
  implementation(libs.bundles.kotest)
}
