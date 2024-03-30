import io.gitlab.arturbosch.detekt.Detekt

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

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(file("${rootProject.projectDir}/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    basePath = projectDir.absolutePath
    reportsDir = file("$projectDir/build/reports/detekt/")
}

tasks.register("detektAll", Detekt::class.java){
    val autoFix = project.hasProperty("detektAutoFix")

    description = "Custom DETEKT build for all modules"
    parallel = true
    ignoreFailures = true
    autoCorrect = autoFix
    buildUponDefaultConfig = true
    setSource(file(projectDir))
    config.setFrom(file("${rootProject.projectDir}/config/detekt/detekt.yml"))
    include("**/*.kt")
    exclude("**/resources/**", "**/build/**")
    reports {
        xml.required.set(false)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(true)
        basePath = projectDir.absolutePath
    }
}
