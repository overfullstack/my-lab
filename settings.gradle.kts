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
      alias("hamcrest-core").to("org.hamcrest:hamcrest:2.2")
      alias("hamcrest-date").to("org.exparity:hamcrest-date:2.0.7")
      alias("java-vavr").to("io.vavr:vavr:0.10.4")
      alias("kotlin-vavr").to("io.vavr:vavr-kotlin:0.10.2")
      alias("jetbrains-annotations").to("org.jetbrains:annotations:21.0.1")
    }
  }
}

rootProject.name = "my-lab"
include("apt")
include("arrow")
include("lombok-lab")
include("kotlin-java-interop")
include("java-lab")
