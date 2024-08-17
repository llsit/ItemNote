plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
}

android {
    namespace = "com.example.core.domain"
}

dependencies {

    // core
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.data)
}