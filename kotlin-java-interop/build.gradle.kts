import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  application // This is need to run main method of Kotlin or Java
}

dependencies {
  implementation(libs.moshi)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)

  implementation(platform("io.arrow-kt:arrow-stack:1.0.1"))
  implementation("io.arrow-kt:arrow-core")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn" + "-Xbackend-threads=0"
    }
  }
}