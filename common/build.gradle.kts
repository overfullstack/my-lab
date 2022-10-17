import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.kotlin.jvm.pluginId)
  alias(libs.plugins.moshix)
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  api(libs.moshix.adapters)
  api(libs.slf4j.api)
  api(libs.graal.sdk)
  api(libs.graal.js)
  api("io.github.serpro69:kotlin-faker:1.10.0")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
      freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
  }
}
moshi {
  enableSealed.set(true)
}
