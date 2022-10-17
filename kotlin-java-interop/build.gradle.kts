import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.kotlin.jvm.pluginId)
}

dependencies {
  implementation(libs.moshi)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
