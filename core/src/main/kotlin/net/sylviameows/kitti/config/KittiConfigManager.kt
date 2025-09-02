package net.sylviameows.kitti.config

import com.akuleshov7.ktoml.file.TomlFileReader
import com.akuleshov7.ktoml.file.TomlFileWriter
import kotlinx.serialization.serializer
import net.sylviameows.kitti.api.config.ConfigManager
import net.sylviameows.kitti.api.config.KittiConfig
import net.sylviameows.kitti.api.config.TomlConfig
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class KittiConfigManager(private val plugin: JavaPlugin) : ConfigManager {
    private val loadedConfigs = mutableMapOf<String, KittiConfig<*>>()

    override fun <T> load(path: String, clazz: Class<T>): KittiConfig<T> {
        val path = plugin.dataFolder.path+"/$path.toml"

        if (loadedConfigs.containsKey(path)) {
            val config = loadedConfigs[path]!!;
            if (clazz.isInstance(config.values)) {
                @Suppress("UNCHECKED_CAST")
                return config as KittiConfig<T>;
            }
            throw IllegalArgumentException("Does not match type.")
        }

        val file = File(path);

        var instance: Any;
        if (file.exists()) {
            instance = TomlFileReader(TomlConfig.input, TomlConfig.output).decodeFromFile(serializer(clazz), path);
        } else {
            val constructor = clazz.getConstructor()
                ?: throw IllegalArgumentException("type ${clazz.name} must have an empty constructor!");
            constructor.isAccessible = true;

            instance = constructor.newInstance() as Any;
            file.parentFile.mkdirs();
            TomlFileWriter(TomlConfig.input, TomlConfig.output).encodeToFile(serializer(clazz), instance as Any, path);
        }

        @Suppress("UNCHECKED_CAST")
        val config = KittiConfig(instance as T, path);

        loadedConfigs[path] = config;
        return config
    }
}