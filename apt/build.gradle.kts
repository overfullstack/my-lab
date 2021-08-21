plugins {
  kotlin("jvm")
  kotlin("kapt")
}

val immutablesVersion = "2.8.8"
dependencies {
  kapt("org.immutables:value:$immutablesVersion")
  compileOnly("org.immutables:builder:$immutablesVersion")
  compileOnly("org.immutables:value-annotations:$immutablesVersion")

  kaptTest("org.immutables:value:$immutablesVersion")
  testCompileOnly("org.immutables:builder:$immutablesVersion")
  testCompileOnly("org.immutables:value-annotations:$immutablesVersion")

  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
}
