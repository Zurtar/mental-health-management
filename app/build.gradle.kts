import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.hilt.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.dokka)
}

android {
    namespace = "com.zurtar.mhma"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zurtar.mhma"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.kotlinx.serialization.json)

    // Hilt & Dagger
    implementation(libs.google.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)

    implementation(libs.kizitonwose.calendar.compose)

    //    From Journal
    implementation(libs.androidx.runtime.livedata)


    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.ui.auth)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)

    implementation(libs.himanshoe.charty)
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Vibe Check: Mental Health Management")
            includes.from(layout.projectDirectory.dir("docs/package-docs.md"))
        }
    }
}

tasks.dokkaHtml {
    doFirst {
        // Clear the existing docs directory before generating new documentation
        val outputDir = layout.projectDirectory.dir("docs/html")
        if (outputDir.asFile.exists()) {
            outputDir.asFile.deleteRecursively()
        }
    }
    outputDirectory.set(layout.projectDirectory.dir("docs/html"))
}

tasks.dokkaJekyll {
    doFirst {
        // Clear the existing docs directory before generating new documentation
        val outputDir = layout.projectDirectory.dir("docs/jekyll")
        if (outputDir.asFile.exists()) {
            outputDir.asFile.deleteRecursively()
        }
    }
    outputDirectory.set(layout.projectDirectory.dir("docs/jekyll"))
}
//Add nav host, serial plugin is fine, need implementations