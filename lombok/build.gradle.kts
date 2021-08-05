import io.freefair.gradle.plugins.lombok.LombokExtension.LOMBOK_VERSION

plugins {
  kotlin("jvm")
  java
  id("io.freefair.lombok") version "6.1.0-m3"
  id("org.sonarqube") version "3.3"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_16

val lombokForSonarQube by configurations.creating
dependencies {
  lombokForSonarQube("org.projectlombok:lombok:$LOMBOK_VERSION")

  implementation("io.vavr:vavr:+")
  implementation("io.vavr:vavr-kotlin:+")

  testImplementation(platform("org.junit:junit-bom:+"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

  implementation("org.slf4j:slf4j-api:+")
  runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:+")
}

sonarqube {
  properties {
    property("sonar.java.libraries", lombokForSonarQube.files.last().toString())
  }
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}
