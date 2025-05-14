package net.sylviameows.kitti

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import net.sylviameows.kitti.api.KittiAPI
import net.sylviameows.kitti.api.KittiBootstrapper
import net.sylviameows.kitti.commands.KittiCommand
import org.bukkit.plugin.java.JavaPlugin

class Bootstrap : KittiBootstrapper() {
    init {
        addCommand(KittiCommand())
    }

    override fun bootstrap(context: BootstrapContext) {
        super.bootstrap(context)
    }

    override fun createPlugin(context: PluginProviderContext): JavaPlugin {
        val instance = super.createPlugin(context);
        if (instance is KittiAPI) {
            KittiAPI.Holder.setInstance(instance);
        }
        return instance;
    }
}