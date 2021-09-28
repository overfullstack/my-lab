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

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      alias("hamcrest-core").to("org.hamcrest:hamcrest:+")
      alias("hamcrest-date").to("org.exparity:hamcrest-date:+")
      alias("java-vavr").to("io.vavr:vavr:+")
      alias("kotlin-vavr").to("io.vavr:vavr-kotlin:+")
      alias("jetbrains-annotations").to("org.jetbrains:annotations:+")
      alias("moshi").to("com.squareup.moshi:moshi:+")
      alias("jackson-databind").to("com.fasterxml.jackson.core:jackson-databind:+")
      alias("commons-collections").to("org.apache.commons:commons-collections4:+")
      alias("commons-lang3").to("org.apache.commons:commons-lang3:+")
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
