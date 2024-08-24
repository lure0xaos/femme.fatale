plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.javafxplugin) apply false
    alias(libs.plugins.org.beryx.jlink) apply false
    alias(libs.plugins.devtools.ksp) apply false
    java
    application
}

repositories {
    mavenCentral()
}
