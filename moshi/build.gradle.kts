plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
  alias(libs.plugins.ksp)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.revoman)
  implementation(libs.moshix.adapters)
  testImplementation(libs.bundles.kotest)
  testImplementation(libs.truth)
}

moshi { enableSealed = true }
