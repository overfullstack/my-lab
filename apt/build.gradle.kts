import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
  kotlin("jvm")
  kotlin("kapt")
  java
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_16

val immutablesVersion = "2.8.8"
dependencies {
  kapt("org.immutables:value:$immutablesVersion")
  compileOnly("org.immutables:builder:$immutablesVersion")
  compileOnly("org.immutables:value-annotations:$immutablesVersion")

  kaptTest("org.immutables:value:$immutablesVersion")
  testCompileOnly("org.immutables:builder:$immutablesVersion")
  testCompileOnly("org.immutables:value-annotations:$immutablesVersion")

  implementation("io.vavr:vavr:+")
  implementation("io.vavr:vavr-kotlin:+")
  
  testImplementation(platform("org.junit:junit-bom:+"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}
