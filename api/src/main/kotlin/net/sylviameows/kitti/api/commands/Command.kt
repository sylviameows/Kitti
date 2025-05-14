package net.sylviameows.kitti.api.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack

interface Command {
    val name: String

    private fun options(): Options {
        return options(Options.from(this));
    }

    fun options(options: Options): Options {
        return options;
    }

    fun execute(context: Context): Result

    fun build(): LiteralCommandNode<CommandSourceStack> {
        return options().build();
    }
}