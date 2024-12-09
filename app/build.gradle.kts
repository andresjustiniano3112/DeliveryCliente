plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt) // Necesario para Room
}

android {
    namespace = "com.example.deliverycliente"
    compileSdk = 35 // Cambiado a la versión soportada más alta

    defaultConfig {
        applicationId = "com.example.deliverycliente"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

    buildTypes {
        debug {
            isDebuggable = true // Asegura que BuildConfig.DEBUG funcione
        }
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

    viewBinding {
        enable = true // Ajuste correcto para Kotlin DSL
    }
}

dependencies {
    // AndroidX y Material
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit para consumo de APIs
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Coroutines para tareas asincrónicas
    implementation(libs.coroutines)

    // Room para almacenamiento local
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // Google Maps para funcionalidades de mapa
    implementation(libs.google.maps)

    // Navigation para manejar fragmentos
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Dependencias de prueba
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // OkHttp Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.squareup.picasso:picasso:2.8")

    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")






}
