import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    idea
    java
    id("io.freefair.lombok") version "4.1.6"
    kotlin("kapt") version "1.3.61"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local/")
}

val arrowSnapshotVersion = "0.11.0-SNAPSHOT"
val arrowVersion = "0.10.4"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.arrow-kt:arrow-core:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-rx2:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-fx-reactor:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-optics:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowSnapshotVersion")
    implementation("io.arrow-kt:arrow-mtl:$arrowVersion")

    kapt("io.arrow-kt:arrow-meta:$arrowSnapshotVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("io.reactivex.rxjava2:rxjava:+")

    testImplementation("org.junit.jupiter:junit-jupiter:+")
    testImplementation("org.amshove.kluent:kluent:+")

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

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "12"
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
