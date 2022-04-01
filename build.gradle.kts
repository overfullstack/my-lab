import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA_PARALLEL

plugins {
  id("com.adarshr.test-logger") version "3.1.0"
  application
}

allprojects {
  group = "com.gakshintala.mylab"
  version = "1.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "application")
  apply(plugin = "com.adarshr.test-logger")
  java.sourceCompatibility = JavaVersion.VERSION_17
  dependencies {
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j18-impl:2.14.1")
  }
  tasks {
    withType<JavaCompile>().configureEach {
      //options.compilerArgs.add("--enable-preview")
    }
    test {
      useJUnitPlatform()
      ignoreFailures = true
      //jvmArgs("--enable-preview")
    }
    testlogger {
      theme = MOCHA_PARALLEL
    }
  }
}
