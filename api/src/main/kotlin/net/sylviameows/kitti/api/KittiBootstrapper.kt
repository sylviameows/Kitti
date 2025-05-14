package net.sylviameows.kitti.api

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.sylviameows.kitti.api.commands.Command

abstract class KittiBootstrapper : PluginBootstrap {
    private val commandsList: MutableList<Command> = ArrayList();

    protected fun addCommand(command: Command) {
        commandsList.add(command);
    }
    protected fun addCommands(vararg commands: Command) {
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
            registrar.register(command.build())
        }
    }

}