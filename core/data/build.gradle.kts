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
    implementation(projects.core.database)
    implementation(projects.core.common)
    api(libs.datastore.preference)
    // coroutines

    // network
    implementation(libs.retrofit)
    implementation(libs.gson.convertor)
    implementation(platform(libs.okhttp.bom))

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytic)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)

    // unit test
    testImplementation(libs.junit)
}