plugins {
  kotlin("jvm")
}

dependencies {
  implementation(platform("io.arrow-kt:arrow-stack:0.13.2"))
  implementation("io.arrow-kt:arrow-core")
  implementation("io.arrow-kt:arrow-fx-coroutines")
  implementation("io.arrow-kt:arrow-optics")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
