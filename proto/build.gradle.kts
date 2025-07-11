plugins {
  id("mylab.sub-conventions")
  alias(libs.plugins.protobuf)
}

dependencies {
  // Protobuf dependencies
  implementation(libs.protobuf.java)
  implementation(libs.protobuf.java.util)
  implementation(libs.jackson.databind)
  implementation(libs.jackson.databind.jsr310)

  // Test dependencies
  testImplementation(libs.bundles.junit)
  testImplementation(libs.assertj.core)
  testImplementation(libs.truth)
}

protobuf {
  protoc { artifact = "com.google.protobuf:protoc:3.25.8" }

  generateProtoTasks {
    all().forEach { task ->
      task.plugins {
        // No additional plugins needed for basic Java generation
      }
    }
  }
}

sourceSets { main { java { srcDirs("build/generated/source/proto/main/java") } } }
