plugins {
    kotlin("android")
    id("com.android.application")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.mijael.mein"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mijael.mein"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "2.1"

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
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.work:work-runtime:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.work:work-runtime:2.9.0")
    //implementation("net.gotev:uploadservice:3.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //PDF
    implementation("com.github.barteksc:android-pdf-viewer:2.0.3")
    implementation("com.itextpdf:itext7-core:7.1.10")
}