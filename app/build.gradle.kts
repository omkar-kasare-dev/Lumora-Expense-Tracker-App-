plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.finance.lumora"
    compileSdk =37

    defaultConfig {
        applicationId = "com.finance.lumora"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.tv.material)
    implementation(libs.firebase.crashlytics.buildtools)

    // Dagger Hilt & ksp
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")


    // Room
    implementation("androidx.room:room-runtime:2.8.0")
    implementation("androidx.room:room-ktx:2.8.0")
    ksp("androidx.room:room-compiler:2.8.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Hilt WorkManager Integration
    implementation("androidx.hilt:hilt-work:1.2.0")
// CRITICAL: Ensure hilt-compiler is added to KSP (or Kapt if using Kapt)
    ksp("androidx.hilt:hilt-compiler:1.2.0")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    // navigation
    implementation("androidx.navigation:navigation-compose:2.9.3")

    // Material3
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-text")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")
    implementation("io.coil-kt:coil-compose:2.6.0")
    // build.gradle.kts (Module level)
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0") // use latest version

    //biometric
    implementation(libs.androidx.biometric)


    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.auth)

    implementation(libs.firebase.firestore)


    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}