val appClass: String = "femme.fatale.MainKt"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.org.beryx.jlink)
    alias(libs.plugins.devtools.ksp)
    java
    application
}

dependencies {
    implementation(project(":model"))
    implementation(project(":log"))
    implementation(project(":kfx"))
    implementation(project(":gen"))
    implementation(kotlin("reflect"))
    ksp(libs.koin.ksp.compiler)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(kotlin("test"))
}

repositories {
    mavenCentral()
}

java {
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
    sourceSets.main {
        kotlin.srcDirs("build/generated/ksp/main/kotlin")
    }
}

application {
    mainClass = appClass
}

jlink {
    addExtraDependencies(
        "javafx.base",
        "javafx.graphics",
        "javafx.controls",
        "javafx.fxml",
        "javafx.swing",
        "javafx.web",
        "javafx.media",
    )
}

tasks.test {
    useJUnitPlatform()
}
