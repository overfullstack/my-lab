plugins {
  kotlin("jvm")
}

dependencies {
  val graalVersion = "22.0.0.2"
  implementation("org.graalvm.sdk:graal-sdk:$graalVersion")
  implementation("org.graalvm.js:js:$graalVersion")
  // necessary to integrate with legacy ScriptEngine based implementation
  implementation("org.graalvm.js:js-scriptengine:$graalVersion")
  implementation(libs.kotlin.vavr)
}

tasks {
  compileKotlin.get().kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
