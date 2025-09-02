package net.sylviameows.kitti.api.commands.context

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.sylviameows.kitti.api.commands.Argument
import org.bukkit.plugin.java.JavaPlugin

class StandardContextProvider<Plugin : JavaPlugin> : ContextProvider<Plugin, Context<Plugin>>() {
    override fun create(
        ctx: CommandContext<CommandSourceStack>,
        arguments: MutableList<Argument<*>>,
        subcommand: String?
    ): Context<Plugin> {
        val builder = Context.Builder<Plugin>();

        process(builder, ctx, arguments, subcommand);

        return builder.build(ctx.source, plugin);
    }
}