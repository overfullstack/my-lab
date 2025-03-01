plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
}

dependencies {
  implementation(project(":common"))
  implementation(libs.graal.js)
  implementation(libs.revoman)
  implementation(libs.okio.jvm)
  implementation(libs.okio.extras)
  implementation(libs.kotlin.vavr)
  implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.3")
  implementation(libs.underscore)
  implementation(libs.moshi)
  implementation(libs.apache.commons.lang3)
}
