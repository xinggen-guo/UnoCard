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

    jvm()

    sourceSets {

        commonMain{
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


        val jvmMain by getting
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

compose.resources {
    packageOfResClass = "com.card.unoshare.generated.resources"
}

// A one-stop task to generate/copy Compose resources for all variants.
afterEvaluate {
    // 可能存在的任务名（不同版本差异很大）
    val candidates = tasks.matching {
        val n = it.name.lowercase()
        n == "generatecomposerescclass" ||
                n.endsWith("processcomposerescources") ||
                n.endsWith("copycomposerescources") ||
                n.contains("composerescources")      // 兜底，包含所有 composeResources 相关任务
    }.toList()

    // 如果你想看一眼都匹配到了哪些任务，可以打印：
    if (candidates.isEmpty()) {
        println("[composeResources] No candidate tasks found yet; it will still try to run generateComposeResClass if present.")
    } else {
        println("[composeResources] Found tasks: " + candidates.joinToString { it.name })
    }

    // 注册聚合任务
    tasks.register("composeResources") {
        group = "compose"
        description = "Generate/copy Compose resources for all targets"
        // 兜底：有些版本只有 generateComposeResClass
        dependsOn(tasks.matching { it.name == "generateComposeResClass" })
        // 依赖所有与 compose resources 相关的任务
        dependsOn(candidates)
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
