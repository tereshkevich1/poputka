plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization.gradle.plugin)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin.gradle.plugin)
    alias(libs.plugins.compose.compiler)
    id ("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.poputka"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.poputka"
        minSdk = 26
        targetSdk = 34
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
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    configurations.all {
        exclude( group = "xmlpull", module = "xmlpull")
    }
}

dependencies {
    implementation(libs.material3)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // Add the dependency for the Firebase Authentication library
    implementation(libs.google.firebase.auth)
    implementation(libs.firebase.appcheck.playintegrity)
    implementation (libs.androidx.browser)
    implementation(libs.firebase.appcheck.debug)

    //pager
    implementation (libs.accompanist.pager)

    //data-store
    implementation (libs.androidx.datastore.preferences)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    ksp(libs.androidx.room.compiler)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.ext.compiler)

    //worker
    implementation (libs.androidx.hilt.work)
    implementation (libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.runtime)

    //retrofit
    implementation (libs.retrofit2.retrofit)
    implementation (libs.okhttp)
    implementation (libs.okhttp.logging.interceptor)
    implementation(libs.converter.gson)

    //location
    implementation(libs.play.services.location)

    implementation (libs.accompanist.permissions)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.safe.args.gradle.plugin)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}