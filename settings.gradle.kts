pluginManagement {
  repositories {
    gradlePluginPortal() // This is for other community plugins
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
  }
  val kotlinVersion: String by settings
  val lombokGradlePluginVersion: String by settings
  val moshiXVersion: String by settings
  plugins {
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.lombok") version kotlinVersion apply false
    id("io.freefair.lombok") version lombokGradlePluginVersion apply false
    kotlin("kapt") version kotlinVersion apply false
    id("dev.zacsweers.moshix") version moshiXVersion apply false
  }
}

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      library("hamcrest-core", "org.hamcrest:hamcrest:2.2")
      library("hamcrest-date", "org.exparity:hamcrest-date:2.0.8")
      library("java-vavr", "io.vavr:vavr:0.10.4")
      library("kotlin-vavr", "io.vavr:vavr-kotlin:0.10.2")
      library("jetbrains-annotations", "org.jetbrains:annotations:23.0.0")
      library("moshi", "com.squareup.moshi:moshi:1.14.0")
      library("jackson-databind", "com.fasterxml.jackson.core:jackson-databind:2.13.4")
      library("apache-commons-lang3", "org.apache.commons:commons-lang3:3.12.0")
      library("apache-commons-collections4", "org.apache.commons:commons-collections4:4.4")
      library("guava", "com.google.guava:guava:31.1-jre")
    }
  }
}

rootProject.name = "my-lab"
include("apt")
include("arrow")
include("kotlin-java-interop")
include("java-lab")
include("common")
include("http4k")
include("moshi-lab")
include("graal-js")
include("kotlin-lab")
