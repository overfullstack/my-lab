@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.lombok)
}
dependencies {
  implementation(libs.vador)
  testImplementation(libs.assertj.vavr)
  testImplementation(libs.assertj.core)
}
