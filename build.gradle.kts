import org.gradle.kotlin.dsl.*

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.build.tools)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.navigation.safe.args)
        classpath(libs.google.services)
    }
}

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin) apply false
}