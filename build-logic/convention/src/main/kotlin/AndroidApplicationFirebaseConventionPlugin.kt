import com.example.itemnote.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
//                apply("com.google.firebase.crashlytics")
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                add("implementation", target.libs.findLibrary("firebase-analytic").get())
                add("implementation", target.libs.findLibrary("firebase-firestore").get())
                add("implementation", target.libs.findLibrary("firebase-auth").get())
                add("implementation", target.libs.findLibrary("firebase-storage").get())
            }

//            extensions.configure<ApplicationExtension> {
//                buildTypes.configureEach {
//                    // Disable the Crashlytics mapping file upload. This feature should only be
//                    // enabled if a Firebase backend is available and configured in
//                    // google-services.json.
//                    configure<CrashlyticsExtension> {
//                        mappingFileUploadEnabled = false
//                    }
//                }
//            }
        }
    }
}
