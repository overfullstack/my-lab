import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA_PARALLEL

allprojects {
  apply(plugin = "mylab.root-conventions")
  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "mylab.sub-conventions")
}
