import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}


android {
    namespace = "com.card.unoshare.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.card.unoshare.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].assets.srcDirs("${rootDir}/shared/src/commonMain/resources")
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    signingConfigs {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        create("upload") {
            storeFile = file("../keystore/guo.keystore")
            storePassword = properties.getProperty("UPLOAD_STORE_PWD") as String
            keyAlias = "cardgame"
            keyPassword = properties.getProperty("UPLOAD_KEY_PWD") as String
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("upload")
        }

        // ✅ Single buildTypes block
        release {
            isMinifyEnabled = true           // shrink + obfuscate with R8
            isShrinkResources = true         // remove unused resources
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("upload")
            // debuggable = false             // default for release
        }

        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            // signingConfig = signingConfigs.getByName("upload") // usually not needed
        }
    }
}

dependencies {
    implementation(project(":shared"))

    // ✅ Compose UI & Material
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.tooling.preview)

    // ✅ AndroidX support
    implementation(libs.androidx.activity.compose)
}