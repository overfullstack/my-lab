allprojects {
  apply(plugin = "mylab.root-conventions")
  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "mylab.sub-conventions")
}
