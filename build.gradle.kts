import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
    id("io.freefair.lombok") version "5.1.0"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
}

val arrowSnapshotVersion = "latest.integration"
val arrowVersion = "latest.integration"

dependencies {
    implementation("io.arrow-kt:arrow-core:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-rx2:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-reactor:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-optics:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-mtl:$arrowVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
    implementation("io.reactivex.rxjava2:rxjava:+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")

    /*testImplementation("org.amshove.kluent:kluent:+") {
        exclude("junit", "junit")
        exclude("org.junit.vintage", "junit-vintage-engine")
    }*/

    implementation("org.codehaus.jackson:jackson-mapper-asl:+")
    implementation("com.google.code.gson:gson:+")
    implementation("com.fasterxml.jackson.core:jackson-databind:+")
    implementation("org.apache.httpcomponents:httpclient:+")
    implementation("com.sun.jersey:jersey-client:+")
    implementation("com.sun.jersey:jersey-json:+")
    implementation("com.twitter:twitter-text:+")
    implementation("org.apache.commons:commons-text:+")
    implementation("io.vavr:vavr:+")

    /*implementation("com.oath.cyclops:cyclops:10.3.0")
    implementation("com.oath.cyclops:cyclops-pure:10.3.0")
    implementation("com.oath.cyclops:cyclops-vavr-integration:10.0.0-M1")
    implementation("com.oath.cyclops:cyclops-anym:10.3.3")*/

    /*compileOnly("org.projectlombok:lombok:+")
    annotationProcessor("org.projectlombok:lombok:+")

    testCompileOnly("org.projectlombok:lombok:+")
    testAnnotationProcessor("org.projectlombok:lombok:+")*/
}

java {
    sourceCompatibility = JavaVersion.VERSION_14
    targetCompatibility = JavaVersion.VERSION_14
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
    options.isWarnings = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_14.toString()
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable") // These are related to Java Kotlin interop
        suppressWarnings = true
    }
}

tasks.withType<Test> {
    ignoreFailures = true
    useJUnitPlatform {
        includeEngines("junit-jupiter")
        excludeEngines("junit-vintage")
    }
    jvmArgs("--enable-preview")
}
