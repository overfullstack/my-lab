plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
}

dependencies {
  implementation(libs.bundles.http4k)
  implementation(libs.bundles.apache.log4j)
}
