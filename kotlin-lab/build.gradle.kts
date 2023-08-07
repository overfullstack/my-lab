plugins { id("mylab.kt-conventions") }

dependencies {
  implementation(project(":common"))
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.klaxon)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.state.machine)
  implementation(libs.rabbitmq.client)
  implementation(libs.bundles.apache.log4j)
  implementation(libs.bundles.kotlin.logging)
  implementation(libs.spring.beans)
  testImplementation(libs.assertj.core)
}
