plugins {
    id("itemnote.android.library")
    id("itemnote.android.library.compose")
    id("itemnote.android.hilt")
}

android {
    namespace = "com.example.core.data"
}

dependencies {

    // core

    // coroutines


    // unit test
    testImplementation(libs.junit)
}