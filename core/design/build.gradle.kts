plugins {
    id("itemnote.android.library")
    id("itemnote.android.library.compose")
}

android {
    namespace = "com.example.design"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.glide)
    implementation(libs.gilde.compose)
    implementation(libs.coil.compose)
    implementation(libs.material)
    implementation(libs.icon.extend)
}