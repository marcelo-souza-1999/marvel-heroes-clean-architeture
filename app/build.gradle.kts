import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.google.ksp)
}

private val nameFileSecrets = "secrets.defaults.properties"

val secretPropertiesFile = rootProject.file("secrets.defaults.properties")
val secretProperties = Properties().apply {
    load(secretPropertiesFile.inputStream())
}

android {
    namespace = "com.marcelo.marvelheroes"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    signingConfigs {
        create("release") {
            storeFile = file(getSecretProperties("STORE_FILE_KEY"))
            storePassword = getSecretProperties("STORE_PASSWORD_KEY")
            keyPassword = getSecretProperties("PASSWORD_KEY")
            keyAlias = getSecretProperties("ALIAS_KEY")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        applicationId = "com.marcelo.marvelheroes"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    android.applicationVariants.configureEach {
        val variantName = name

        kotlin.sourceSets {
            getByName(variantName) {
                kotlin.srcDir("build/generated/ksp/$variantName/kotlin")
            }
        }
    }

    val beta by buildTypes.registering {
        applicationIdSuffix = ".beta"
        isMinifyEnabled = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro",
            "proguard-rules-beta.pro"
        )
    }

    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        beta {
            initWith(beta.get())
            initWith(getByName("debug"))
        }
        named("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

secrets {
    defaultPropertiesFileName = nameFileSecrets
}

dependencies {
    implementation(libs.bundles.androidx)
    implementation(libs.android.material.design)

    implementation(libs.koin.dev)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    api(platform(libs.ok.http.bom))
    implementation(libs.bundles.ok.http)

    implementation(libs.bundles.retrofit)
    implementation(libs.gson)

    implementation(libs.pagination.ktx)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.glide)

    implementation(libs.shimmer)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(libs.datastore.ktx)

    implementation(libs.bundles.navigation)

    implementation(libs.bundles.lifecycle)

    testImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.bundles.instrumented.test)
    debugImplementation(libs.fragment.testing)
}

private fun getSecretProperties(key: String): String {
    val file = rootProject.file(nameFileSecrets)

    if (file.exists()) {
        val properties = Properties()
        properties.load(FileInputStream(file))
        return properties.getProperty(key)
    }

    throw FileNotFoundException()
}
