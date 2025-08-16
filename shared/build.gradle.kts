plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jbCompose)

    kotlin("plugin.compose") version "2.0.20" // Kotlin Compose Compiler
    id("org.jetbrains.kotlin.native.cocoapods")
    kotlin("plugin.serialization") version "2.0.20"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {

        commonMain{
            resources.srcDirs("src/commonMain/resources")
            dependencies {
                with(compose) {
                    implementation(runtime)
                    implementation(ui)
                    implementation(foundation)
                    implementation(material)
                    implementation(components.resources)
                    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
                    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                }
            }
        }

        val iosMain = create("iosMain") {
            dependsOn(getByName("commonMain"))
        }
        getByName("iosX64Main").dependsOn(iosMain)
        getByName("iosArm64Main").dependsOn(iosMain)
        getByName("iosSimulatorArm64Main").dependsOn(iosMain)

    }

    cocoapods {
        summary = "Shared code for UnoCard"
        homepage = "https://github.com/xinggen-guo/UnoCard"
        version = "1.16.2"
        ios.deploymentTarget = "16.0"
        framework {
            baseName = "shared"
        }
    }
}

android {

    sourceSets["main"].assets.srcDirs("src/androidMain/assets")

    namespace = "com.card.unoshare"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
//dependencies {
//    implementation(libs.androidx.cardview)
//}


//tasks.register<Copy>("copyCommonResourcesToAssets") {
//    from("src/commonMain/resources")
//    into("src/androidMain/assets")
//}
//
//tasks.named("preBuild") {
//    dependsOn("copyCommonResourcesToAssets")
//}
