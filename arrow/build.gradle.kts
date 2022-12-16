@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.ksp)
}
dependencies {
  implementation(libs.bundles.arrow)

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.guava)
  implementation(libs.apache.commons.lang3)
  implementation(libs.bundles.kotest)
}

// ksp plugin not compatible with k2 compiler
tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
