plugins {
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
  alias(libs.plugins.kotlin.noarg)
  alias(libs.plugins.ksp)
}

dependencies {
  ksp(libs.arrow.optics.ksp.plugin)
  implementation(project(":common"))
  implementation(libs.http4k.format.moshi)
  implementation(libs.bundles.arrow)
  implementation(libs.moshix.adapters)
  testImplementation(libs.bundles.kotest)
}

noArg {
  annotation("ga.overfullstack.NoArg")
  invokeInitializers = true
}

moshi { enableSealed = true }
