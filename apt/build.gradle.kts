@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.kotlin.kapt.pluginId)
  id(libs.plugins.kotlin.jvm.pluginId)
  alias(libs.plugins.kotlin.lombok)
  alias(libs.plugins.lombok)
}
kapt {
  keepJavacAnnotationProcessors = true
}
kotlinLombok {
  lombokConfigurationFile(file("lombok.config"))
}
dependencies {
  val autoValueVersion = "1.9"
  compileOnly("com.google.auto.value:auto-value-annotations:${autoValueVersion}")
  kapt("com.google.auto.value:auto-value:${autoValueVersion}")
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
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
