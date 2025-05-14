```
  /\_/\  (
 ( ^.^ ) _)
   \"/  (     kitti: a minecraft plugin library 
 ( | | )    developed by sylviameows
(__d b__)
```
---
## What is this?
Kitti is an open-source paper plugin development library used for all my kotlin plugins. It provides kotlin standard library and has useful helper classes for registering brigadier commands with less boilerplate.

### Goals
I don't have much planned for this library yet, but I hope to include some things like:
- Custom Items, Potions, and Enchantments.
- Custom inventory menus.
- Custom book guis.

## Usage
```kts
repositories {
    maven {
        name = "sylviameows"
        url = uri("https://repo.sylviameo.ws/releases/")
    }
}

dependencies {
    // replace with latest version
    compileOnly("net.sylviameows:kitti:0.3.0")
}
```

## Contributing
Feel free to help by opening a [pull request](https://github.com/sylviameows/Kitti/pulls) or [issue](https://github.com/sylviameows/Kitti/issues)!