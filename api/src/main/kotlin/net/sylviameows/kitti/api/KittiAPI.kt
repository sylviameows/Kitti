package net.sylviameows.kitti.api

import net.sylviameows.kitti.api.config.ConfigManager
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.ApiStatus

interface KittiAPI {
    companion object {
        fun instance(): KittiAPI {
            return Holder.INSTANCE
        }

        fun getConfigManager(plugin: JavaPlugin): ConfigManager {
            return instance().getConfigManager(plugin);
        }
    }

    fun getConfigManager(plugin: JavaPlugin): ConfigManager;

    @ApiStatus.Internal
    object Holder {
        internal lateinit var INSTANCE: KittiAPI;

        fun setInstance(api: KittiAPI) {
            INSTANCE = api
        }
    }
}