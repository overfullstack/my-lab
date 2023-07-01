plugins { id("mylab.kt-conventions") }

dependencies {
  implementation(libs.http4k.core)
  implementation(libs.http4k.format.moshi)
  implementation(libs.http4k.format.jackson)
}
