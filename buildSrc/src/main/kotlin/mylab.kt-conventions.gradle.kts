import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins { kotlin("jvm") }

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies { testImplementation(libs.kotestBundle) }

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xcontext-parameters", "-progressive", "-Xmulti-dollar-interpolation") } }

tasks.withType<KotlinJvmCompile>().configureEach {
  jvmTargetValidationMode = JvmTargetValidationMode.WARNING
}
