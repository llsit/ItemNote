plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
}

android {
    namespace = "com.example.core.data"
}

dependencies {

    // core
    api(projects.core.model)
    implementation(projects.core.network)

    // coroutines

    // network
    implementation(libs.retrofit)
    implementation(libs.gson.convertor)
    implementation(platform(libs.okhttp.bom))

    // unit test
    testImplementation(libs.junit)
}