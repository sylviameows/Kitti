plugins {
    kotlin("jvm") version "2.1.20"
}

group = "net.sylviameows"
version = properties["plugin_version"] ?: "unspecified"

ext {
    set("plugin_id", properties["plugin_id"])
    set("plugin_version", properties["plugin_version"])

    set("api_version", properties["api_version"])
    set("paper_version", properties["paper_version"])
}

allprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()

        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:${rootProject.ext["paper_version"]}")
    }

    group = rootProject.group
    version = rootProject.version

//    apply(plugin = "checkstyle")

//    checkstyle {
//        toolVersion = "10.21.4"
//    }
}

task("version") {
    println(project.version)
}

