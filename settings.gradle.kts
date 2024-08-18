enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ItemNote"
include(":app")
include(":core")
include(":core:design")
include(":feature:detail")
include(":feature:recipe")
include(":core:data")
include(":core:network")
include(":core:model")
include(":core:domain")
include(":feature:note")
