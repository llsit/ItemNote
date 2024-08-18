plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // model
    implementation(projects.core.model)
    // API
    implementation(libs.retrofit)
    implementation(libs.gson.convertor)
    // json parsing
    implementation(libs.kotlinx.serialization.json)
}