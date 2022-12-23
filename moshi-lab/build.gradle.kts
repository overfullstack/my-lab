@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.moshi)
  implementation(libs.moshix.adapters)
  testImplementation(libs.bundles.kotest)
}

moshi {
  enableSealed.set(true)
}
// moshix plugin not compatible with k2 compiler
tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
