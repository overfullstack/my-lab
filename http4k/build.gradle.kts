plugins {
  kotlin("jvm")
}

dependencies {
  val http4kVersion = "4.25.15.0"
  implementation("org.http4k:http4k-core:$http4kVersion")
  implementation("org.http4k:http4k-format-moshi:$http4kVersion")
  implementation("org.http4k:http4k-format-jackson:$http4kVersion")
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
