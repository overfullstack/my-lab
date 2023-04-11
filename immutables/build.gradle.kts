import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("mylab.kt-conventions")
  id(libs.plugins.kotlin.kapt.pluginId)
}
dependencies {
  kapt(libs.immutables.value)
  compileOnly(libs.immutables.builder)
  compileOnly(libs.immutables.value.annotations)

  kaptTest(libs.immutables.value)
  testCompileOnly(libs.immutables.builder)
  testCompileOnly(libs.immutables.value.annotations)

  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.guava)
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      // ! -Xjvm-default=all" is needed for Immutables to work with Kotlin default methods
      freeCompilerArgs = listOf("-Xjvm-default=all")
    }
  }
}
