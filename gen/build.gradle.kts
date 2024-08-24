val appClass: String = "femme.fatale.MainKt"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    java
}

dependencies {
    implementation(project(":log"))
    testImplementation(kotlin("test"))
}

repositories {
    mavenCentral()
}

java {
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

tasks.test {
    useJUnitPlatform()
}
