import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  java
  id("com.adarshr.test-logger") version "3.0.0"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.squareup.moshi:moshi:+")
  runtimeOnly("com.squareup.moshi:moshi-adapters:+")

  compileOnly("org.jetbrains:annotations:+")
  testCompileOnly("org.jetbrains:annotations:+")
  
  implementation("io.vavr:vavr:+")
  implementation("io.vavr:vavr-kotlin:+")

  testImplementation("org.hamcrest:java-hamcrest:+")
  
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
  compileJava {
    options.compilerArgs.add("--enable-preview")
    options.isWarnings = false
  }

  compileKotlin {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_16.toString()
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
