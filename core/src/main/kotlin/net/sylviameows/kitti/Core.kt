package net.sylviameows.kitti

import io.papermc.paper.plugin.configuration.PluginMeta
import net.sylviameows.kitti.api.KittiAPI
import org.bukkit.plugin.java.JavaPlugin

class Core : KittiAPI, JavaPlugin() {
    companion object {
        lateinit var META: PluginMeta;
    }

    override fun onEnable() {
        logger.info("KittiLib initialized.")

        META = pluginMeta
    }
}