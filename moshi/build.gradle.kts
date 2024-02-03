plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
  alias(libs.plugins.ksp)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.revoman)
  testImplementation(libs.bundles.kotest)
  testImplementation(libs.truth)
}

val generatedAnnotation = "javax.annotation.processing.Generated"
ksp { arg("moshi.generated", generatedAnnotation) }

moshi { enableSealed = true }
