plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.kotlin.lombok)
  alias(libs.plugins.lombok)
}
kotlinLombok {
  lombokConfigurationFile(file("lombok.config"))
}
dependencies {
  val autoValueVersion = "1.10.1"
  compileOnly("com.google.auto.value:auto-value-annotations:$autoValueVersion")
  annotationProcessor("com.google.auto.value:auto-value:$autoValueVersion")
  annotationProcessor(libs.immutables.value)
  compileOnly(libs.immutables.builder)
  compileOnly(libs.immutables.value.annotations)

  testAnnotationProcessor(libs.immutables.value)
  testCompileOnly(libs.immutables.builder)
  testCompileOnly(libs.immutables.value.annotations)

  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.guava)
}
