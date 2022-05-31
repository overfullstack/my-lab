import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
}

dependencies {
  val graalVersion = "22.1.0"
  implementation("org.graalvm.sdk:graal-sdk:$graalVersion")
  implementation("org.graalvm.js:js:$graalVersion")
  implementation(libs.kotlin.vavr)
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
  implementation("com.github.javadev:underscore:1.77")
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
