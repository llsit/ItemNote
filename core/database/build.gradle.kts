plugins {
    id("itemnote.android.library")
    id("itemnote.android.hilt")
    alias(libs.plugins.ksp)
}


android {
    namespace = "com.example.core.database"

    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
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
}