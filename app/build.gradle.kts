plugins {
    kotlin("kapt")
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.google.service)
    alias(libs.plugins.compose.compiler)

    id("itemnote.android.application.compose")
    id("itemnote.android.application")
}

android {
    namespace = "com.example.itemnote"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.itemnote"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // core
    implementation(projects.core.design)
    implementation(projects.core.model)
    implementation(projects.core.design)
    implementation(projects.core.data)
    implementation(projects.core.common)

    // feature
    implementation(projects.feature.recipe)
    implementation(projects.feature.detail)
    implementation(projects.feature.note)
    implementation(projects.feature.authentication)

    // Di
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //design
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.navigation)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

//ktlint {
//    android = true
//    version = "0.43.2" // Replace with the desired KtLint version
//    verbose = true
//    debug = true
//    outputToConsole = true
//    outputColorName = "RED"
//    ignoreFailures = false
//    enableExperimentalRules = true
//}