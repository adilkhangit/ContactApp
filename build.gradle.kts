// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
buildscript {
    dependencies {
        // Google Services plugin
        classpath("com.google.gms:google-services:4.4.0")

        // Firebase Crashlytics Gradle Plugin
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")

        classpath("com.google.firebase:perf-plugin:1.4.2")

    }
}

