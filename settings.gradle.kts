plugins {
  id("com.autonomousapps.build-health") version "2.4.2"
  id("org.jetbrains.kotlin.jvm") version "2.0.21" apply false
}

dependencyResolutionManagement {
  versionCatalogs { create("libs") { from(files("libs.versions.toml")) } }
  pluginManagement {
    repositories {
      mavenCentral()
      gradlePluginPortal()
      google()
      maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
      maven("https://oss.sonatype.org/content/repositories/snapshots")
      maven("https://repo.spring.io/milestone")
    }
  }
}

rootProject.name = "my-lab"

include("apt")

include("arrow")

include("java-lab")

include("common")

include("http4k")

include("moshi")

include("graal-js")

include("kotlin-lab")

include("immutables")
