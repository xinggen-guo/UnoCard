enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "UnoCard"

include(":androidApp")
include(":iosApp")
include(":shared")
include(":desktopApp")

project(":androidApp").projectDir = file("androidApp")
project(":iosApp").projectDir = file("iosApp")
project(":shared").projectDir = file("shared")
project(":desktopApp").projectDir = file("desktopApp")