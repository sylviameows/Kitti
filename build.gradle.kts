plugins {
    kotlin("jvm") version "2.2.10"
}

group = "net.sylviameows"
version = properties["plugin_version"] ?: "unspecified"

ext {
    set("plugin_id", properties["plugin_id"])
    set("plugin_version", properties["plugin_version"])
    set("minecraft_version", properties["minecraft_version"])
}

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "kotlin")

    group = rootProject.group
    version = rootProject.version

    repositories {
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:${properties["minecraft_version"]}-R0.1-SNAPSHOT")
    }
//    apply(plugin = "checkstyle")

//    checkstyle {
//        toolVersion = "10.21.4"
//    }
}

task("version") {
    println(project.version)
}

