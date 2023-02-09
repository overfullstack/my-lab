dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("libs.versions.toml"))
    }
  }
  pluginManagement {
    repositories {
      mavenCentral()
      gradlePluginPortal()
      google()
      maven("https://oss.sonatype.org/content/repositories/snapshots")
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
include("vador-lab")
