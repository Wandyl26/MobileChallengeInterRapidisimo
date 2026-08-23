plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.example.intercommerceapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.intercommerceapp"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    @Suppress("DEPRECATION")
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.materialIcons)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.retrofit2)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    androidTestImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)

    implementation(libs.runtime.livedata)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.nav.version)
    androidTestImplementation(libs.nav.testing)

    androidTestImplementation(libs.core.testing)


    implementation(libs.coil)
}