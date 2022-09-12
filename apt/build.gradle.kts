plugins {
  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.lombok")
  id("io.freefair.lombok")
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
  val immutablesVersion: String by project
  kapt("org.immutables:value:$immutablesVersion")
  compileOnly("org.immutables:builder:$immutablesVersion")
  compileOnly("org.immutables:value-annotations:$immutablesVersion")

  kaptTest("org.immutables:value:$immutablesVersion")
  testCompileOnly("org.immutables:builder:$immutablesVersion")
  testCompileOnly("org.immutables:value-annotations:$immutablesVersion")

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
