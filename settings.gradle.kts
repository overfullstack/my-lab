pluginManagement {
    repositories {
        gradlePluginPortal() // This is for other community plugins
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
    val kotlinEap: String by settings
    plugins {
        kotlin("jvm") version kotlinEap // This is handy if there are multiple modules. This lets you declare version at one place.
        kotlin("kapt") version kotlinEap
    }
}

rootProject.name = "my-lab"
