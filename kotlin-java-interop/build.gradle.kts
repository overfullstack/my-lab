import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
}

dependencies {
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_16.toString()
      freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
  }
}
