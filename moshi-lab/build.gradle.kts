plugins {
  kotlin("jvm")
}

dependencies {
  implementation(project(":common"))
  implementation(libs.moshi)
  implementation("dev.zacsweers.moshix:moshi-adapters:0.17.1")
}
