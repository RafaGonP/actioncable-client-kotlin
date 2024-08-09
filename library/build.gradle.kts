import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    `maven-publish`
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
