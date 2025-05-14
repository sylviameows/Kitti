package net.sylviameows.kitti.api.commands

import io.papermc.paper.command.brigadier.CommandSourceStack

typealias RequirementFunction = (CommandSourceStack) -> Boolean

class Requirements {
    private val functions: MutableList<RequirementFunction> = ArrayList();

    fun add(function: RequirementFunction): Requirements {
        functions.add(function);
        return this;
    }

    fun permission(permission: String): Requirements {
        functions.add {
            it.sender.hasPermission(permission)
        }
        return this;
    }

    fun check(source: CommandSourceStack): Boolean {
        for (function in functions) {
            if (!function.invoke(source)) return false;
        }
        return true;
    }
}