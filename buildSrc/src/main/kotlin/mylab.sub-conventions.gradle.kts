import com.adarshr.gradle.testlogger.theme.ThemeType
import com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep.XML
import kotlinx.kover.api.DefaultJacocoEngine
import kotlinx.kover.api.KoverTaskExtension
import org.gradle.api.JavaVersion
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType

plugins {
  application
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}
dependencies {
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
tasks {
  withType<Test>().configureEach {
    useJUnitPlatform()
    ignoreFailures = true
  }
}