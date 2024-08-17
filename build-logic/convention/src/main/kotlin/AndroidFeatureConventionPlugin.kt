import com.android.build.gradle.LibraryExtension
import com.example.itemnote.configureAndroidCompose
import com.example.itemnote.configureKotlinAndroid
import com.example.itemnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
      }

      dependencies {
        add("implementation", project(":core:design"))
//        add("implementation", project(":core:navigation"))
        add("implementation", project(":core:data"))
        add("implementation", project(":core:network"))
//        add("compileOnly", project(":core:preview"))

        // Add lifecycle dependencies
        add("implementation", target.libs.findLibrary("androidx-lifecycle-viewmodel-ktx").get())
        add("implementation", target.libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())

      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        configureAndroidCompose(this)
        defaultConfig.targetSdk = 34
      }

      extensions.getByType<KotlinAndroidProjectExtension>().apply {
        configureKotlinAndroid(this)
      }
    }
  }
}
