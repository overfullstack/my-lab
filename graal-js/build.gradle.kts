plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
}

dependencies {
  implementation(project(":common"))
  implementation(libs.graal.sdk)
  implementation(libs.graal.js)
  implementation(libs.kotlin.vavr)
  implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.0")
  implementation("com.github.javadev:underscore:1.100")
  implementation(libs.moshi)
  implementation(libs.apache.commons.lang3)
}
