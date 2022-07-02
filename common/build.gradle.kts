import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("dev.zacsweers.moshix") version "0.18.3"
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  val http4kVersion = "4.27.1.0"
  api("org.http4k:http4k-core:$http4kVersion")
  api("org.http4k:http4k-format-moshi:$http4kVersion")
  api("dev.zacsweers.moshix:moshi-adapters:0.18.3")
  api("org.slf4j:slf4j-api:1.7.36")
  val graalVersion = "22.1.0"
  api("org.graalvm.sdk:graal-sdk:$graalVersion")
  api("org.graalvm.js:js:$graalVersion")
  api("io.github.serpro69:kotlin-faker:1.10.0")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
moshi {
  enableSealed.set(true)
}
