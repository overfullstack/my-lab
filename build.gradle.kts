import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
  id("mylab.root-conventions")
  id(libs.plugins.kover.get().pluginId)
  id(libs.plugins.gradle.doctor.get().pluginId)
  id(libs.plugins.dependency.analysis.get().pluginId)
  id(libs.plugins.detekt.get().pluginId)
}

doctor { 
  javaHome { 
    failOnError = false
  }
}

allprojects {
  repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.spring.io/milestone")
  }
}

dependencies {
  val subProjectsForKover = setOf("immutables")
  subprojects
    .filter { subProjectsForKover.contains(it.name) }
    .forEach { kover(project(":${it.name}")) }
}

kover { reports { total { html { onCheck = true } } } }

val detektReportMerge by
  tasks.registering(ReportMergeTask::class) {
    output = project.layout.buildDirectory.file("reports/detekt/merge.sarif")
  }

subprojects {
  tasks {
    withType<Detekt>().configureEach {
      finalizedBy(detektReportMerge)
      reports { sarif.required = true }
    }
  }

  detektReportMerge { input.from(tasks.withType<Detekt>().map { it.sarifReportFile }) }
}
