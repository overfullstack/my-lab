plugins {
  id("mylab.kt-conventions")
}
dependencies {
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.moshi)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.state.machine)
  testImplementation(libs.assertj.core)
}
