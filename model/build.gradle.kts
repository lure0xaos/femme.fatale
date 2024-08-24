val appClass: String = "femme.fatale.MainKt"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `java-library`
}

dependencies {
    api(libs.kotlin.serialization.core)
    implementation(project(":gen"))
    testImplementation(kotlin("test"))
    testImplementation(libs.kotlin.serialization.json)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(project(":log"))
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
