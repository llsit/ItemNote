plugins {
    id("itemnote.android.library")
    id("itemnote.android.library.compose")
}

android {
    namespace = "com.example.core.design"
}

dependencies {
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    api(libs.androidx.appcompat)
    api(platform(libs.androidx.compose.bom))
    api(libs.glide)
    api(libs.gilde.compose)
    api(libs.coil.compose)
    api(libs.material)
    api(libs.icon.extend)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
}