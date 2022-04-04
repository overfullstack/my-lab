plugins {
  kotlin("jvm")
  id("dev.zacsweers.moshix") version "0.17.1"
}

dependencies {
  implementation(project(":common"))
  implementation(libs.moshi)
  implementation("dev.zacsweers.moshix:moshi-adapters:0.17.1")

  val kotestVersion = "5.2.2"
  testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
  testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
}

moshi {
  enableSealed.set(true)
}
