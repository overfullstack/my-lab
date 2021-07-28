import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("kapt")
  java
  //id("io.freefair.lombok") version "6.1.0-m3"
  id("com.adarshr.test-logger") version "3.0.0"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

//val lombokVersion = "1.18.20"
val immutablesVersion = "2.8.8"
dependencies {
  /*kapt("org.projectlombok:lombok:$lombokVersion")
  compileOnly("org.projectlombok:lombok:$lombokVersion")
  testCompileOnly("org.projectlombok:lombok:$lombokVersion")*/

  kapt("org.immutables:value:$immutablesVersion")
  compileOnly("org.immutables:builder:$immutablesVersion")
  compileOnly("org.immutables:value-annotations:$immutablesVersion")

  kaptTest("org.immutables:value:$immutablesVersion")
  testCompileOnly("org.immutables:builder:$immutablesVersion")
  testCompileOnly("org.immutables:value-annotations:$immutablesVersion")

  implementation("com.squareup.moshi:moshi:+")
  runtimeOnly("com.squareup.moshi:moshi-adapters:+")

  compileOnly("org.jetbrains:annotations:+")
  testCompileOnly("org.jetbrains:annotations:+")

  // ARROW --->
  implementation(platform("io.arrow-kt:arrow-stack:1.0.0-SNAPSHOT"))
  implementation("io.arrow-kt:arrow-core")
  implementation("io.arrow-kt:arrow-fx-coroutines")
  implementation("io.arrow-kt:arrow-optics")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
  implementation("io.vavr:vavr:+")
  implementation("io.vavr:vavr-kotlin:+")

  testImplementation(platform("org.junit:junit-bom:+"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  
  testImplementation("org.junit-pioneer:junit-pioneer:+")
  testImplementation("org.junit.jupiter:junit-jupiter-params")
  testImplementation("org.mockito:mockito-core:3.11.1")
  
  implementation("org.slf4j:slf4j-api:+")
  runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:+")
}

java.sourceCompatibility = JavaVersion.VERSION_16

tasks {
  withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
    options.isWarnings = false
  }

  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_16.toString()
      freeCompilerArgs =
        listOf("-Xjsr305=strict", "-Xjvm-default=enable") // These are related to Java Kotlin interop
      suppressWarnings = true
    }
  }

  test {
    useJUnitPlatform()
    ignoreFailures = true
    jvmArgs("--enable-preview")
  }

  testlogger {
    setTheme("mocha")
    showExceptions = true
    showStackTraces = false
    showFullStackTraces = false
    showCauses = false
    slowThreshold = 2000
    showSummary = true
    showSimpleNames = true
    showPassed = true
    showSkipped = true
    showFailed = true
    showStandardStreams = false
    showPassedStandardStreams = false
    showSkippedStandardStreams = false
    showFailedStandardStreams = false
  }
}
