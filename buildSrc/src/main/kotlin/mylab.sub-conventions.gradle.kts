import org.gradle.kotlin.dsl.invoke

plugins {
  application
}
java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter("5.9.2")
    }
  }
}
