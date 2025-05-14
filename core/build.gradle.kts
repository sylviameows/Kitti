plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    shadow("org.jetbrains.kotlin:kotlin-stdlib:2.1.20")
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