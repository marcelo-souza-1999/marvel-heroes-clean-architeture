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

val detektFormat = libs.detekt.format

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        val pathDetektConfig = "config/detekt/detekt.yml"
        config = rootProject.files(pathDetektConfig)
    }

    dependencies {
        detektPlugins(detektFormat)
    }
}