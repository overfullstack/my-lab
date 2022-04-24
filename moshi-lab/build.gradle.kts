import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("dev.zacsweers.moshix") version "0.17.1"
}

dependencies {
  implementation(project(":common"))
  implementation(libs.moshi)
  
  val moshiXVersion = "0.17.1"
  implementation("dev.zacsweers.moshix:moshi-adapters:$moshiXVersion")

  val kotestVersion = "5.2.3"
  testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
  testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
}

moshi {
  enableSealed.set(true)
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
