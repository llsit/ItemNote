plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.core.network"
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