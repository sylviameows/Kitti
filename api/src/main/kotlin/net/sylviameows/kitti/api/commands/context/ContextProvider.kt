package net.sylviameows.kitti.api.commands.context

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.sylviameows.kitti.api.commands.Argument
import org.bukkit.plugin.java.JavaPlugin


abstract class ContextProvider<Plugin : JavaPlugin, C : Context<Plugin>> {
    lateinit var plugin: Plugin;

    abstract fun create(
        ctx: CommandContext<CommandSourceStack>,
        arguments: MutableList<Argument<*>>,
        subcommand: String? = null
    ): C

    fun process(builder: ContextBuilder, ctx: CommandContext<CommandSourceStack>, arguments: MutableList<Argument<*>>, subcommand: String?) {
        if (!subcommand.isNullOrEmpty()) builder.subcommand(subcommand)

        arguments.forEachIndexed { index, argument ->
            try {
                val name = argument.name ?: "arg$index"
                val value = ctx.getArgument(name, argument.resultType);
                if (value != null) builder.set(name, value)
            } catch (_: IllegalArgumentException) {

            }
        }
    }
}