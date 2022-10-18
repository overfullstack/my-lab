@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.moshi)
  implementation(libs.moshix.adapters)
  testImplementation(libs.bundles.kotest)
}

moshi {
  enableSealed.set(true)
}
