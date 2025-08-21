plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.jbCompose)
    application
}

kotlin {
    jvm {
        compilations.all {
            compileTaskProvider.get().dependsOn(":shared:generateComposeResClass")
        }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(libs.jb.compose.runtime)
                implementation(libs.jb.compose.foundation)
                implementation(libs.jb.compose.material3)
                implementation(libs.compose.desktop)
            }
        }
    }
}

application {
    mainClass.set("com.card.unoshare.MainKt")
}