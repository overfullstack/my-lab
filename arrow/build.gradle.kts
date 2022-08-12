plugins {
  kotlin("jvm")
}

dependencies {
  implementation(platform("io.arrow-kt:arrow-stack:1.1.2"))
  implementation("io.arrow-kt:arrow-core")
  implementation("io.arrow-kt:arrow-fx-coroutines")
  implementation("io.arrow-kt:arrow-optics")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
  implementation("com.google.guava:guava:31.1-jre")
  implementation(libs.apache.commons.lang3)

  val kotestVersion = "5.4.2"
  testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
  testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
  testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
