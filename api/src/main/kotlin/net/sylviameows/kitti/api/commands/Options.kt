package net.sylviameows.kitti.api.commands

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.sylviameows.kitti.api.commands.context.Context
import net.sylviameows.kitti.api.commands.context.ContextProvider
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.ApiStatus

class Options private constructor(
    private val name: String,
    private val callback: (Context<*>) -> Result,
    private val parent: Options? = null
) {
    private val arguments: MutableList<Argument<*>> = ArrayList()
    private val subcommands: MutableList<Options> = ArrayList()

    private val requirements: Requirements = Requirements();

    companion object {
        /** A base options instance for a command */
        fun <P : JavaPlugin, C : Context<P>> from(command: RawCommand<P, C>, update: (Options) -> Options = { it }): Options {
            val options = Options(command.name, { command.execute(it as C) })
            return update(options);
        }
    }

    fun requirement(function: RequirementFunction): Options {
        requirements.add(function)
        return this;
    }

    fun permission(permission: String): Options {
        requirements.permission(permission);
        return this;
    }

    fun subcommand(name: String, update: (Options) -> Options = { it }): Options {
        subcommands.add(update(Options(name, callback, this)))
        return this
    }

    fun argument(name: String, type: Argument<out Any>, update: (Argument<out Any>) -> Argument<out Any> = { it }): Options {
        arguments.add(update(type.name(name)))
        return this
    }

    // easier function to use as it only requires an argument type.
    inline fun <reified T : Any> argument(name: String, type: ArgumentType<T>, noinline update: (Argument<out Any>) -> Argument<out Any> = { it }): Options {
        return argument(name, Argument.Companion.of(type), update)
    }

    /** Will return a string of the subcommands leading to the context. */
    private fun getSubcommandContext(): String? {
        if (parent == null) return null;

        var subcommand = name;
        var parent = this.parent;

        while (parent != null) {
            if (parent.parent == null) break;
            subcommand = parent.name + " " + subcommand;
            parent = parent.parent;
        }

        return subcommand;
    }

    /** Converts KittiOptions into brigadier usable nodes. */
    @ApiStatus.Internal
    fun convert(provider: ContextProvider<*, *>): LiteralArgumentBuilder<CommandSourceStack> {
        // create a base node for this option.
        val literal = Commands.literal(name);
        val subcommand = getSubcommandContext();

        // runs the command callback with generated context, then processes the result.
        literal.executes {
            val context = provider.create(it, arguments, subcommand)
            process(callback(context), context)
        }
        literal.requires { requirements.check(it) }

        // we have to do this backwards, otherwise it will not build the nodes correctly.
        var child: ArgumentBuilder<CommandSourceStack, *>? = null;

        arguments.asReversed().forEachIndexed { index, argument ->
            val name = argument.name ?: "arg${arguments.size-index-1}"

            val node = Commands.argument(name, argument.argumentType)
            node.executes {
                val context = provider.create(it, arguments, subcommand);
                process(callback(context), context) }
            node.requires { argument.requirements.check(it) }

            if (argument.suggestions != null) node.suggests(argument.suggestions)

            if (child != null) {
                node.then(child)
            }

            child = node;
        }

        if (child != null) {
            literal.then(child);
        }

        for (sub in subcommands) {
            literal.then(sub.convert(provider))
        }

        return literal;
    }

    private fun process(result: Result, context: Context<*>): Int {
        if (result.isError()) {
            if (result.reason != null) {
                context.source.sender.sendMessage(Component.text(result.reason).color(NamedTextColor.RED))

                return 1;
            }
        }

        return result.output;
    }

    fun build(provider: ContextProvider<*, *>): LiteralCommandNode<CommandSourceStack> {
        return convert(provider).build()
    }
}