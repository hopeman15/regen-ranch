import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("org.jmailen.kotlinter") version "3.16.0"
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "org.jetbrains.kotlinx.kover")

    detekt {
        buildUponDefaultConfig = false
        allRules = false
        config.setFrom("$rootDir/detekt/default-detekt-config.yml")
    }
    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
        reports {
            xml.required.set(false)
            html.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
        }
        exclude("**/resources/**")
        exclude("**/build/**")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

// Kover
dependencies {
    kover(project(":app"))
}

koverReport {
    filters {
        excludes {
            classes("*BuildConfig")
            annotatedBy("*Preview")
        }
    }

    defaults {
        xml {
            onCheck = false
            setReportFile(layout.buildDirectory.file("$buildDir/reports/kover/result.xml"))
        }
        html {
            onCheck = false
            setReportDir(layout.buildDirectory.dir("$buildDir/reports/kover/html-result"))
        }
    }
}
