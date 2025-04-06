plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  id(libs.plugins.kotlin.kapt.get().pluginId)
}

dependencies {
  kapt(libs.immutables.value)
  compileOnly(libs.immutables.builder)
  compileOnly(libs.immutables.value.annotations)

  kaptTest(libs.immutables.value)
  testCompileOnly(libs.immutables.builder)
  testCompileOnly(libs.immutables.value.annotations)

  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  compileOnly(libs.jetbrains.annotations)
}

kapt {
  useBuildCache = true
}

tasks.withType<Jar> {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kotlin { compilerOptions { freeCompilerArgs.add("-Xjvm-default=all") } }
