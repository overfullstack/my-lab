import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0

plugins { kotlin("jvm") }

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies { testImplementation(libs.kotestBundle) }

kotlin {
  compilerOptions {
    languageVersion.set(KOTLIN_2_0)
    apiVersion.set(KOTLIN_2_0)
    freeCompilerArgs.addAll("-Xcontext-receivers")
  }
}
