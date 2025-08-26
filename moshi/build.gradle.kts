plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
  alias(libs.plugins.moshix)
  alias(libs.plugins.power.assert)
  alias(libs.plugins.ksp)
}

dependencies {
  implementation(project(":common"))
  implementation(libs.revoman)
  implementation(libs.moshix.adapters)
  testImplementation(libs.bundles.kotest)
  testImplementation(libs.truth)
}

powerAssert {
  functions = listOf("io.kotest.matchers.shouldBe")
}

moshi { enableSealed = true }
