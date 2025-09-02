package net.sylviameows.kitti.api.commands.context

import io.papermc.paper.command.brigadier.CommandSourceStack
import net.sylviameows.kitti.api.commands.exceptions.ContextNullException
import net.sylviameows.kitti.api.commands.exceptions.ContextTypeException
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.ApiStatus

/**
 * The standard context instance. Contains the commands argument data, plugin,
 */
class Context<Plugin : JavaPlugin> private constructor(
    val arguments: Map<String, Any?>,
    val source: CommandSourceStack,
    val plugin: Plugin,
    val subcommand: String?
) {
    val count: Int
        get() = arguments.size;

    fun has(key: String): Boolean {
        return arguments.contains(key)
    }

    @Throws(ContextTypeException::class)
    inline fun <reified T : Any> get(key: String): T? {
        val value = arguments[key]

        // return early as next step requires it be non-null.
        if (value == null) {
            return value;
        }
        if (value !is T) {
            // throw an exception with the expected and actual type of the key.
            throw ContextTypeException(key, T::class.java.name, value.javaClass.name)
        }

        // give back the value if it matches the expected type.
        return value;
    }

    @Throws(ContextNullException::class)
    inline fun <reified T : Any> getRequired(key: String): T {
        // return value if non-null, otherwise provide null exception.
        return get<T>(key) ?: throw ContextNullException(key);
    }

    @ApiStatus.Internal
    class Builder<Plugin : JavaPlugin> : ContextBuilder {
        private val arguments: MutableMap<String, Any> = HashMap();
        private var subcommand: String? = null;

        override fun set(key: String, value: Any): Builder<Plugin> {
            arguments[key] = value
            return this;
        }

        override fun subcommand(subcommand: String): Builder<Plugin> {
            this.subcommand = subcommand;
            return this;
        }

        fun build(source: CommandSourceStack, plugin: Plugin): Context<Plugin> {
            return Context(arguments.toMap(), source, plugin, subcommand)
        }
    }
}