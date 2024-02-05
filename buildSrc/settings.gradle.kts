// This is for gradle plugins etc. to be used within buildSrc
dependencyResolutionManagement {
  versionCatalogs { create("libs") { from(files("../libs.versions.toml")) } }
  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.spring.io/milestone")
  }
}
