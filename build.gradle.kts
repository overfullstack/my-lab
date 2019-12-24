plugins {
    kotlin("jvm") version "1.3.61"
    idea
    java
    id("io.freefair.lombok") version "4.1.6"
}

group = "com.gakshintala.mylab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:+")

    implementation("org.codehaus.jackson:jackson-mapper-asl:+")
    implementation("com.google.code.gson:gson:+")
    implementation("com.fasterxml.jackson.core:jackson-databind:+")
    implementation("org.apache.httpcomponents:httpclient:+")
    implementation("com.sun.jersey:jersey-client:+")
    implementation("com.sun.jersey:jersey-json:+")
    implementation("com.twitter:twitter-text:+")
    implementation("org.apache.commons:commons-text:+")
    implementation("io.vavr:vavr:+")
    implementation("com.oath.cyclops:cyclops:10.3.0")
    implementation("com.oath.cyclops:cyclops-pure:10.3.0")
    implementation("com.oath.cyclops:cyclops-vavr-integration:10.0.0-M1")
    implementation("com.oath.cyclops:cyclops-anym:10.3.3")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "12"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "12"
    }
}