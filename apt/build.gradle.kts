plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  alias(libs.plugins.kotlin.lombok)
  alias(libs.plugins.lombok)
}

kotlinLombok { lombokConfigurationFile(file("lombok.config")) }

dependencies {
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.guava)
}
