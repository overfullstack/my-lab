import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("mylab.kt-conventions")
}

dependencies {
  implementation(libs.moshi)
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}