pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
  }
}

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("libs.versions.toml"))
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
