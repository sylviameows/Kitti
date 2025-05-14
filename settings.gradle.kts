pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "Kitti"

include("api")
var api = project(":api")
api.name = "KittiAPI"

include("core")
var core = project(":core")
core.name = "KittiCore"