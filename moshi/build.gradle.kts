plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
  alias(libs.plugins.ksp)
}

dependencies {
  ksp(libs.arrow.optics.ksp.plugin)
  implementation(project(":common"))
  implementation(libs.bundles.arrow)
  implementation(libs.moshi)
  implementation(libs.moshi.kotlin)
  implementation(libs.moshix.adapters)
  testImplementation(libs.bundles.kotest)
}
moshi {
  enableSealed by true
  generateProguardRules by false
}
