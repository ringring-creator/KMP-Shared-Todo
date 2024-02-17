import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
            api(libs.sqlDelight.common)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            api(compose.components.resources)
            api(compose.materialIconsExtended)
            api(libs.voyager.navigator)
            api(libs.voyager.kodein)
            implementation(libs.stately.common)
        }
        androidMain.dependencies {
            implementation(libs.sqlDelight.android)
        }
        iosMain.dependencies {
            api(libs.sqlDelight.ios)
        }
        jvmMain.dependencies {
            implementation(libs.sqlDelight.sqlite)
            implementation(libs.voyager.navigator.desktop)
            api(libs.voyager.kodein.desktop)
            implementation(libs.voyager.screenmodel.desktop)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.ring.ring.kmpsharedtodo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

sqldelight {
    databases {
        create("LocalDb") {
            packageName.set("data.local.db")
            srcDirs("src/commonMain/kotlin")
        }
    }
}