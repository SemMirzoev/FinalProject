plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)

    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    //lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    //roomDatabase
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.room.compiler)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    //Okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp3.logging.interceptor)

    //Coil
    implementation(libs.coil)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Dagger Hilt
    //noinspection UseTomlInstead
    implementation("com.google.dagger:hilt-android:2.46.1")
    //noinspection UseTomlInstead
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")

    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.airbnb.android:lottie:3.4.0")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")

    




}
