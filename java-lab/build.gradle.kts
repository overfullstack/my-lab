dependencies {
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.moshi)
  implementation(libs.jackson.databind)
}
