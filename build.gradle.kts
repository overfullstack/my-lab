import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
    id("io.freefair.lombok") version "5.3.3.3"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val arrowSnapshotVersion = "latest.integration"
val arrowVersion = "latest.integration"

dependencies {
    implementation("io.arrow-kt:arrow-core:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-rx2:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-reactor:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-coroutines:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-optics:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-mtl:$arrowVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
    implementation("io.reactivex.rxjava2:rxjava:+")

    testImplementation(platform("org.junit:junit-bom:5.8.0-M1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("org.codehaus.jackson:jackson-mapper-asl:+")
    implementation("com.google.code.gson:gson:+")
    implementation("com.fasterxml.jackson.core:jackson-databind:+")
    implementation("org.apache.httpcomponents:httpclient:+")
    implementation("com.sun.jersey:jersey-client:+")
    implementation("com.sun.jersey:jersey-json:+")
    implementation("com.twitter:twitter-text:+")
    implementation("org.apache.commons:commons-text:+")
    implementation("io.vavr:vavr:+")

    testImplementation("org.hamcrest:hamcrest:2.2")

}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
    options.isWarnings = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_15.toString()
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable") // These are related to Java Kotlin interop
        suppressWarnings = true
    }
}

tasks.withType<Test> {
    ignoreFailures = true
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}
