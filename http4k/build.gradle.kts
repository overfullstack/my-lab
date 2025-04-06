plugins {
  id("mylab.sub-conventions")
  id("mylab.kt-conventions")
}

dependencies {
  implementation(platform(libs.http4k.bom))
  implementation(libs.bundles.http4k)
  implementation(libs.bundles.apache.log4j)
}
