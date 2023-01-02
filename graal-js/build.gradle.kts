import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.kotlin.jvm.pluginId)
}

dependencies {
  implementation(libs.graal.sdk)
  implementation(libs.graal.js)
  implementation(libs.kotlin.vavr)
  implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.1")
  implementation("com.github.javadev:underscore:1.85")
  implementation(libs.moshi)
  implementation(libs.apache.commons.lang3)
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
