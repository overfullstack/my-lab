plugins { id("mylab.sub-conventions") }

dependencies {
  implementation(project(":common"))
  implementation(libs.java.vavr)
  implementation(libs.kotlin.vavr)
  implementation(libs.moshi)
  implementation(libs.jackson.databind)
  implementation(libs.json)
  implementation(libs.apache.commons.collections4)
  implementation(libs.guava)
  implementation(libs.revoman)
  implementation(libs.bundles.vador)
  implementation(libs.spring.beans)

  testImplementation(libs.assertj.vavr)
  testImplementation(libs.assertj.core)
}
