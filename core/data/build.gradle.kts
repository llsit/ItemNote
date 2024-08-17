plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
}

android {
    namespace = "com.example.core.data"
}

dependencies {

    // core
    implementation(projects.core.model)
    implementation(projects.core.network)

    // coroutines


    // unit test
    testImplementation(libs.junit)
}