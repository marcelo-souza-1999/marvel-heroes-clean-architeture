import java.util.Properties
import java.io.FileInputStream
import java.io.FileNotFoundException

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    alias(libs.plugins.google.ksp)
}

val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties().apply {
    load(apikeyPropertiesFile.inputStream())
}

val releasePropertiesFile = rootProject.file("release.properties")
val releaseProperties = Properties().apply {
    load(releasePropertiesFile.inputStream())
}

android {
    namespace = "com.marcelo.marvelheroes"
    compileSdk = libs.versions.compile.sdk.get().toInt()

/*    signingConfigs {
        create("release") {
            storeFile = file(getReleaseProperties("STORE_FILE_KEY"))
            storePassword = getReleaseProperties("STORE_PASSWORD_KEY")
            keyPassword = getReleaseProperties("PASSWORD_KEY")
            keyAlias = getReleaseProperties("ALIAS_KEY")
        }
    }*/
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        applicationId = "com.marcelo.marvelheroes"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"

        buildConfigField("String", "PUBLIC_KEY", getApiKeyProperties("PUBLIC_KEY"))
        buildConfigField("String", "PRIVATE_KEY", getApiKeyProperties("PRIVATE_KEY"))
        buildConfigField("String", "BASE_URL_API", getApiKeyProperties("BASE_URL_API"))
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
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
/*        named("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }*/
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

dependencies {
    implementation(libs.bundles.androidx)
    implementation(libs.android.material.design)

    implementation(libs.koin.dev)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    api(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    api(platform(libs.ok.http.bom))
    implementation(libs.ok.http.squareup)
    implementation(libs.ok.http.interceptor)

    implementation(libs.bundles.retrofit)
    implementation(libs.gson)

    implementation(libs.pagination.ktx)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.glide)

    implementation(libs.shimmer)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.datastore.ktx)

    implementation(libs.bundles.navigation)

    implementation(libs.bundles.lifecycle)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.koin.test.ktx)

    androidTestImplementation(libs.koin.test.ktx)
    androidTestImplementation(libs.koin.test.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.orchestrator)
    androidTestImplementation(libs.ok.http.mock.test)
    debugImplementation(libs.fragment.testing)
}

fun getApiKeyProperties(key: String): String {
    val file = rootProject.file("apikey.properties")

    if (file.exists()) {
        val properties = Properties()
        properties.load(FileInputStream(file))
        return properties.getProperty(key)
    }

    throw FileNotFoundException()
}

fun getReleaseProperties(key: String): String {
    val file = rootProject.file("release.properties")

    if (file.exists()) {
        val properties = Properties()
        properties.load(FileInputStream(file))
        return properties.getProperty(key)
    }

    throw FileNotFoundException()
}
