plugins {
  kotlin("jvm")
  kotlin("kapt")
}

val immutablesVersion = "2.9.1"
dependencies {
  kapt("org.immutables:value:$immutablesVersion")
  compileOnly("org.immutables:builder:$immutablesVersion")
  compileOnly("org.immutables:value-annotations:$immutablesVersion")

  kaptTest("org.immutables:value:$immutablesVersion")
  testCompileOnly("org.immutables:builder:$immutablesVersion")
  testCompileOnly("org.immutables:value-annotations:$immutablesVersion")

  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
