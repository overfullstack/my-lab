import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

plugins {
  id("io.gitlab.arturbosch.detekt")
}
val detektReportMerge by tasks.registering(ReportMergeTask::class) {
  output.set(rootProject.buildDir.resolve("reports/detekt/merge.xml"))
}
tasks.withType<Detekt>().configureEach {
  reports {
    html.required by true
    sarif.required by false
    txt.required by false
    xml.required by false
  }
}
plugins.withType<DetektPlugin> {
  tasks.withType<Detekt> detekt@{
    finalizedBy(detektReportMerge)
    detektReportMerge.configure {
      input.from(this@detekt.xmlReportFile)
    }
  }
}
