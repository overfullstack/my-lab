import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.kotlin.jvm.pluginId)
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

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
