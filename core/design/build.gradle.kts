plugins {
    id("itemnote.android.library")
    id("itemnote.android.library.compose")
}

android {
    namespace = "com.example.design"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}