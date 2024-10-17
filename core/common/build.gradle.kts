plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
    id("itemnote.android.common")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.core.common"
}

dependencies {

    // core
    api(libs.timber)
}