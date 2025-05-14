plugins {
    `maven-publish`
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