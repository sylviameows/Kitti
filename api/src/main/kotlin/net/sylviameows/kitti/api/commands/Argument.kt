package net.sylviameows.kitti.api.commands

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.suggestion.SuggestionProvider
import io.papermc.paper.command.brigadier.CommandSourceStack

class Argument<T : Any>(
    val argumentType: ArgumentType<T>,
    val resultType: Class<T>
) {
    var name: String? = null;
    val requirements: Requirements = Requirements()
    var suggestions: SuggestionProvider<CommandSourceStack>? = null;

    fun name(name: String): Argument<T> {
        this.name = name;
        return this;
    }

    fun requirement(function: RequirementFunction): Argument<T> {
        requirements.add(function)
        return this;
    }

    fun permission(permission: String): Argument<T> {
        requirements.permission(permission);
        return this;
    }

    companion object {
        inline fun <reified T : Any> of(type: ArgumentType<T>): Argument<T> {
            return Argument(type, T::class.java)
        }
    }
}