pluginManagement {
  repositories {
    gradlePluginPortal() // This is for other community plugins
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
  }
  val kotlinVersion: String by settings
  val lombokGradlePluginVersion: String by settings
  plugins {
    kotlin("jvm") version kotlinVersion // This is handy if there are multiple modules. This lets you declare version at one place.
    kotlin("kapt") version kotlinVersion
    id("io.freefair.lombok") version lombokGradlePluginVersion
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
      library("moshi", "com.squareup.moshi:moshi:1.13.0")
      library("jackson-databind", "com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    }
  }
}

rootProject.name = "my-lab"
include("apt")
include("arrow")
include("lombok-lab")
include("kotlin-java-interop")
include("java-lab")
include("common")
include("http4k")