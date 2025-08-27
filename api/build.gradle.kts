plugins {
    `maven-publish`
}


repositories {
    mavenCentral()
}

dependencies {
    api("com.akuleshov7:ktoml-core:0.7.1")
    api("com.akuleshov7:ktoml-file:0.7.1")
}

publishing {
    repositories {
        maven {
            name = "sylviameowsReleases"
            url = uri("https://repo.sylviameo.ws/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.sylviameows"
            artifactId = "kitti"
            version = rootProject.version.toString()
            from(components["java"])
        }
    }
}