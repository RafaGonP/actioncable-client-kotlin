import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin")
    maven
}

group = "com.github.RafaGonP"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {

    artifacts {
        archives(jar)
    }
}

dependencies {
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.KOTLIN_COROUTINES)
    implementation(Libs.OKHTTP)
    implementation(Libs.GSON)
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.MOCKWEBSERVER)
}
