plugins { `kotlin-dsl` }
// These are the list of gradle plugins used inside the buildSrc
dependencies {
  implementation(libs.kotlin.gradle)
  implementation(libs.kotlin.spring.gradle)
  implementation(libs.spring.dependency.management.gradle)
  implementation(libs.spring.boot.gradle)
  implementation(libs.spotless.gradle)
  implementation(libs.detekt.gradle)
  implementation(libs.kover.gradle)
  implementation(libs.spotbugs.gradle)
  implementation(libs.testLogger.gradle)
  implementation(libs.gradle.doctor.gradle)
  implementation(libs.dependency.analysis.gradle)
}
