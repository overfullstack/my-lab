enableFeaturePreview("VERSION_CATALOGS")

// This is for build.gradle.kts in this repo
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") { from(files("../libs.versions.toml")) }
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
