plugins {
  kotlin("jvm")
}

dependencies {
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_16.toString()
}
