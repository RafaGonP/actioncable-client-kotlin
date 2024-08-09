import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    `maven-publish`
}

android {
    compileSdkVersion(Versions.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = "com.RafaGonP.action.cable.client.sample"
        minSdkVersion(Versions.MIN_SDK_VERSION)
        targetSdkVersion(Versions.TARGET_SDK_VERSION)
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets["main"].withConvention(KotlinSourceSet::class) {
        kotlin.srcDir("src/main/kotlin")
    }
}

group = "com.github.RafaGonP"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.KOTLIN_COROUTINES)
    implementation(Libs.OKHTTP)
    implementation(Libs.GSON)
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.MOCKWEBSERVER)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.rafagonp"
                artifactId = "actioncable-client"
                version = "1.1"

                afterEvaluate {
                    from(components["release"])
                }
            }
        }
        repositories {
            maven {
                name = "rafagonp_myrepo"
                url = uri("${project.buildDir}/repo")
            }
        }
    }
}
