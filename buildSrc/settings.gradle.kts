// This is for gradle plugins etc. to be used within buildSrc
dependencyResolutionManagement {
  versionCatalogs { create("libs") { from(files("../libs.versions.toml")) } }
  repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    gradlePluginPortal()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    google()
  }
}
