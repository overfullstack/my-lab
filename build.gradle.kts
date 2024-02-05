import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
  id("mylab.root-conventions")
  id(libs.plugins.kover.get().pluginId)
  id(libs.plugins.gradle.doctor.get().pluginId)
  id(libs.plugins.dependency.analysis.get().pluginId)
  id(libs.plugins.detekt.get().pluginId) apply false
}

dependencies {
  val subProjectsForKover = setOf("immutables")
  subprojects
    .filter { subProjectsForKover.contains(it.name) }
    .forEach { kover(project(":${it.name}")) }
}

koverReport { defaults { html { onCheck = true } } }

val detektReportMerge by
  tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
  }

allprojects {
  repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.spring.io/milestone")
  }
}

subprojects {
  tasks.withType<Detekt>().configureEach { reports { html.required = true } }
  plugins.withType<DetektPlugin> {
    tasks.withType<Detekt> detekt@{
      finalizedBy(detektReportMerge)
      detektReportMerge.configure { input.from(this@detekt.htmlReportFile) }
    }
  }
}
