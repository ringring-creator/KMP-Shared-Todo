import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
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

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.sqlDelight.android)
        }
        iosMain.dependencies {
            implementation(libs.sqlDelight.ios)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqlDelight.common)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.kodein)
            implementation(libs.stately.common)
            implementation(libs.napier)
            implementation(libs.multiplatformSettings.common)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.sqlDelight.sqlite)
            implementation(libs.voyager.navigator.desktop)
            implementation(libs.voyager.kodein.desktop)
            implementation(libs.voyager.screenmodel.desktop)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.ring.ring.kmpsharedtodo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.ring.ring.kmpsharedtodo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.koin.android)
        implementation(libs.koin.androidx.compose)
    }
    buildFeatures {
        buildConfig = true
    }
}

compose.desktop {
    application {
        mainClass = "com.ring.ring.kmpsharedtodo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.ring.ring.kmpsharedtodo"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("LocalDb") {
            packageName.set("com.ring.ring.kmpsharedtodo.data.local.db")
            srcDirs("src/commonMain/kotlin")
        }
    }
}