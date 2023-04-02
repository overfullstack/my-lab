plugins {
  id("mylab.kt-conventions")
  id(libs.plugins.spring.boot.pluginId)
  id(libs.plugins.spring.dependency.management.pluginId)
  id(libs.plugins.kotlin.spring.pluginId)
}

dependencies {
  implementation(libs.spring.boot.starter.batch)
  implementation(libs.kotlin.reflect)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.spring.batch.test)
}
