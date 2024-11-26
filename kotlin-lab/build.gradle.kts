plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.revoman)
  implementation(libs.kotlinx.serialization)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.datetime)
  implementation(libs.klaxon)
  implementation(libs.json)
  implementation(libs.gson)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.state.machine)
  implementation(libs.rabbitmq.client)
  implementation(libs.bundles.apache.log4j)
  implementation(libs.bundles.kotlin.logging)
  implementation(libs.spring.beans)
  implementation(libs.pprint)
  testImplementation(libs.assertj.core)
}
