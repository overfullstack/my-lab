plugins {
  kotlin("jvm")
}

dependencies {
  implementation(libs.http4k.core)
  implementation(libs.http4k.format.moshi)
  implementation(libs.http4k.format.jackson)
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
