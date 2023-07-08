dependencyResolutionManagement {
  versionCatalogs { create("libs") { from(files("libs.versions.toml")) } }
  pluginManagement {
    repositories {
      mavenCentral()
      gradlePluginPortal()
      google()
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
