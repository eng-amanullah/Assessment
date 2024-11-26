plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

val baseUrl: String by project

android {
    namespace = "com.amanullah.assessment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.amanullah.assessment"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", "BASE_URL", baseUrl)
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

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

    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.material)
    implementation (libs.coil.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    // Hilt dependency injection
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.swiperefreshlayout)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.converter.scalars)

    // Okhttp
    implementation (libs.okhttp)
    implementation (libs.okhttp.interceptor)

    // Interceptor
    implementation (libs.okhttpprofiler)

    implementation(libs.work.manager)
    implementation(libs.work.manager.dagger)

    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.work.manager)
    implementation(libs.work.manager.dagger)

    implementation(libs.androidx.media)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room.paging)
}