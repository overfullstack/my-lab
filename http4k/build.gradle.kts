plugins {
  kotlin("jvm")
}

val http4kVersion = "4.25.5.2"
dependencies {
  implementation("org.http4k:http4k-format-moshi:$http4kVersion")
  implementation("org.http4k:http4k-format-jackson:$http4kVersion")
  
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}