plugins {
  id("mylab.kt-conventions")
}
dependencies {
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.moshi)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.state.machine)
  implementation(libs.rabbitmq.client)
  implementation(libs.bundles.apache.log4j)
  implementation(libs.bundles.kotlin.logging)
  testImplementation(libs.assertj.core)
}
