plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.javafxplugin)
    alias(libs.plugins.devtools.ksp)
    `java-library`
}

dependencies {
    implementation(project(":log"))
    api(libs.kotlin.serialization.json)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.javafx)
    api(libs.koin.core)
    api(libs.koin.coroutines)
    api(libs.koin.annotations)
    api(libs.kotlinx.datetime)
    ksp(libs.koin.ksp.compiler)
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
}

repositories {
    mavenCentral()
}

java {
}

javafx {
    version = libs.versions.javafx.get()
    modules = listOf(
        "javafx.base",
        "javafx.graphics",
        "javafx.controls",
        "javafx.fxml",
        "javafx.swing",
        "javafx.web",
        "javafx.media",
    )
    configurations = arrayOf<String>("api")
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
    sourceSets.main {
        kotlin.srcDirs("build/generated/ksp/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}
