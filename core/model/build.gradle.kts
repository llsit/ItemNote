plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.core.model"
}

dependencies {

    implementation(libs.kotlinx.serialization.json)
}