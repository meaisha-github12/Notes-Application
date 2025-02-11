plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.22-1.0.16"

}

android {
    namespace = "com.example.takenotes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.takenotes"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.generativeai)
    val voyagerVersion = "1.1.0-beta02"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    val composeVersion = "1.5.1"

    implementation(platform(libs.androidx.compose.bom)) // For BOM alignment
    implementation(libs.androidx.foundation) // Using foundation with explicit version
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.ui.tooling.preview)
    implementation(libs.ui.tooling.preview)  // Add this line
    debugImplementation(libs.ui.tooling)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.library.base)
    implementation(libs.androidx.room.runtime) // Room Runtime
    implementation(libs.androidx.room.ktx) // Kotlin Extensions
    ksp(libs.androidx.room.compiler) // KSP Compiler instead of KAPT
    implementation("io.coil-kt:coil-compose:2.6.0")

}