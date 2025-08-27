package net.sylviameows.kitti

import io.papermc.paper.plugin.configuration.PluginMeta
import net.sylviameows.kitti.api.KittiAPI
import net.sylviameows.kitti.api.config.ConfigManager
import net.sylviameows.kitti.config.KittiConfigManager
import org.bukkit.plugin.java.JavaPlugin

class Core : KittiAPI, JavaPlugin() {
    companion object {
        lateinit var META: PluginMeta;
    }

    override fun onEnable() {
        logger.info("KittiLib initialized.")

        META = pluginMeta
    }

    private val configManagers: MutableMap<String, ConfigManager> = HashMap()
    override fun getConfigManager(plugin: JavaPlugin): ConfigManager {
        if (configManagers.containsKey(plugin.name)) {
            return configManagers[plugin.name]!!
        }

        val manager = KittiConfigManager(plugin);
        configManagers[plugin.name] = manager;
        return manager;
    }
}