package net.sylviameows.kitti.api.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.sylviameows.kitti.api.commands.context.Context
import net.sylviameows.kitti.api.commands.context.ContextProvider
import net.sylviameows.kitti.api.commands.Options
import org.bukkit.plugin.java.JavaPlugin

/**
 * Raw typed command, best used if using a custom Context type for your plugin.
 * This type is not recommended for standard use cases, reference Command<Plugin>.
 * @see Command
 */
interface RawCommand<Plugin : JavaPlugin, C : Context<Plugin>> {
    val name: String

    private fun options(): Options {
        return options(Options.from(this));
    }

    fun options(options: Options): Options {
        return options;
    }

    fun execute(context: C): Result

    fun build(provider: ContextProvider<Plugin, C>): LiteralCommandNode<CommandSourceStack> {
        return options().build(provider);
    }
}