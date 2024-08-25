package com.example.itemnote

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *,*>,
) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        lint {
            abortOnError = false
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()
}

internal fun Project.configureKotlinAndroid(
    extension: KotlinAndroidProjectExtension,
) {
    extension.apply {
        compilerOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors.set(
                properties["warningsAsErrors"] as? Boolean ?: false
            )

            freeCompilerArgs.set(
                freeCompilerArgs.getOrElse(emptyList()) + listOf(
                    "-Xcontext-receivers",
                    "-Xopt-in=kotlin.RequiresOptIn",
                    // Enable experimental coroutines APIs, including Flow
                    "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    // Enable experimental compose APIs
                    "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
                    "-Xopt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
                )
            )

            // Set JVM target to 17
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

/**
 * Configure base Kotlin options
 */
private inline fun <reified T : KotlinTopLevelExtension> Project.configureKotlin() = configure<T> {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors: String? by project
    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> TODO("Unsupported project extension $this ${T::class}")
    }.apply {
        jvmTarget = JvmTarget.JVM_17
        allWarningsAsErrors = warningsAsErrors.toBoolean()
        freeCompilerArgs.add(
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }
}
