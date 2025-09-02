package net.sylviameows.kitti.api

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.sylviameows.kitti.api.commands.RawCommand
import net.sylviameows.kitti.api.commands.context.Context
import net.sylviameows.kitti.api.commands.context.ContextProvider
import net.sylviameows.kitti.api.commands.context.StandardContextProvider
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UnstableApiUsage")
abstract class KittiBootstrapper<Plugin : JavaPlugin> : PluginBootstrap {
    private val commandsList: MutableList<RawCommand<Plugin, Context<Plugin>>> = ArrayList();
    protected val contextProvider = initContextProvider();

    protected fun initContextProvider(): ContextProvider<Plugin, Context<Plugin>> {
        return StandardContextProvider();
    }

    protected fun addCommand(command: RawCommand<Plugin, Context<Plugin>>) {
        commandsList.add(command);
    }
    protected fun addCommands(vararg commands: RawCommand<Plugin, Context<Plugin>>) {
        commands.forEach(this::addCommand)
    }

    override fun bootstrap(context: BootstrapContext) {
        val manager = context.lifecycleManager;

        manager.registerEventHandler(LifecycleEvents.COMMANDS) {
            this.commands(it);
        }
    }

    protected fun commands(event: ReloadableRegistrarEvent<Commands>) {
        val registrar = event.registrar();

        this.commandsList.forEach { command ->
            registrar.register(command.build(contextProvider))
        }
    }

    override fun createPlugin(context: PluginProviderContext): JavaPlugin {
        val plugin = plugin(context)
        contextProvider.plugin = plugin;
        return plugin;
    }

    abstract fun plugin(context: PluginProviderContext): Plugin;
}