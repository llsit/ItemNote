plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
    alias(libs.plugins.ksp)
}


android {
    namespace = "com.example.core.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    // core
    implementation(projects.core.model)

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // json parsing
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.gson)
}