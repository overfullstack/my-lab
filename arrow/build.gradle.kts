plugins {
  kotlin("jvm")
}

dependencies {
  implementation(platform("io.arrow-kt:arrow-stack:1.1.2"))
  implementation("io.arrow-kt:arrow-core")
  implementation("io.arrow-kt:arrow-fx-coroutines")
  implementation("io.arrow-kt:arrow-optics")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
