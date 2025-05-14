package net.sylviameows.kitti.api.commands

import io.papermc.paper.command.brigadier.CommandSourceStack
import net.sylviameows.kitti.api.commands.exceptions.ContextNullException
import net.sylviameows.kitti.api.commands.exceptions.ContextTypeException
import org.jetbrains.annotations.ApiStatus.Internal
import kotlin.jvm.Throws

class Context private constructor(
    val arguments: Map<String, Any?>,
    val source: CommandSourceStack,
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

    @Internal
    class Builder {
        private val arguments: MutableMap<String, Any> = HashMap();
        private var subcommand: String? = null;

        fun set(key: String, value: Any): Builder {
            arguments[key] = value
            return this;
        }

        fun subcommand(subcommand: String): Builder {
            this.subcommand = subcommand;
            return this;
        }

        fun build(source: CommandSourceStack): Context {
            return Context(arguments.toMap(), source, subcommand)
        }
    }
}