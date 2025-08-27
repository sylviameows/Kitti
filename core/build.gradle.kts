plugins {
    id("com.gradleup.shadow") version "9.0.2"
}

repositories {
    mavenCentral()
}

dependencies {
    shadow("org.jetbrains.kotlin:kotlin-stdlib:2.2.10")
    implementation(project(":KittiAPI"))
}

tasks.processResources {
    filesMatching("paper-plugin.yml") {
        expand(ext.properties)
    }
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.jar {
    archiveClassifier = "light"
}

tasks.shadowJar {
    archiveClassifier = null
}