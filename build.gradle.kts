plugins {
    application
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.serialization)
}

group = "ro.horatiu_udrea"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.client.java)
    implementation(libs.skrapeit)
    implementation(libs.slf4j)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.yaml)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.platform)
}

// Force update for vulnerable transitive dependency from it.skrape:skrapeit-html-parser:1.3.0-alpha.2
configurations.all {
    resolutionStrategy {
        force("org.jsoup:jsoup:1.17.2")
    }
}

application {
    mainClass = "ro.horatiu_udrea.cs_ubb_timetable_parser.MainKt"
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}