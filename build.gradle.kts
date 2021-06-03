import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    java
    //id("io.freefair.lombok") version "6.0.0-m2"
    id("org.barfuin.gradle.taskinfo") version "1.0.5"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

//val lombokVersion = "1.18.20"
val immutablesVersion = "2.8.8"
dependencies {
    /*kapt("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")*/
    
    kapt("org.immutables:value:$immutablesVersion")
    compileOnly("org.immutables:builder:$immutablesVersion")
    compileOnly("org.immutables:value-annotations:$immutablesVersion")
    testCompileOnly("org.immutables:builder:$immutablesVersion")
    testCompileOnly("org.immutables:value-annotations:$immutablesVersion")

    compileOnly("org.jetbrains:annotations:+")
    testCompileOnly("org.jetbrains:annotations:+")
    
    // ARROW --->
    implementation(platform("io.arrow-kt:arrow-stack:1.0.0-SNAPSHOT"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")
    implementation("io.arrow-kt:arrow-optics")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
    implementation("io.vavr:vavr:+")
    implementation("org.exparity:hamcrest-date:2.0.7")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("de.cronn:reflection-util:2.10.0")

    testImplementation(platform("org.junit:junit-bom:+"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")


    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.junit-pioneer:junit-pioneer:1.3.8")
}

java.sourceCompatibility = JavaVersion.VERSION_16

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("--enable-preview")
        options.isWarnings = false
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_16.toString()
            freeCompilerArgs =
                listOf("-Xjsr305=strict", "-Xjvm-default=enable") // These are related to Java Kotlin interop
            suppressWarnings = true
        }
    }

    withType<Test> {
        ignoreFailures = true
        useJUnitPlatform()
        jvmArgs("--enable-preview")
    }
    
}
