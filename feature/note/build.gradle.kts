plugins {
    alias(libs.plugins.compose.compiler)
    id("itemnote.android.feature")
    id("itemnote.android.hilt")
}

android {
    namespace = "com.example.feature.note"
}
