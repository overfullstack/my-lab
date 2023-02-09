// This is for gradle plugins etc. to be used within buildSrc
dependencyResolutionManagement {
  versionCatalogs {
    create("libs") { from(files("../libs.versions.toml")) }
  }
  repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
  }
}
