package net.sylviameows.kitti.api.commands.exceptions

class ContextNullException(private val argument: String) : Exception() {
    override val message: String
        get() = "Required argument '$argument' is not found in the execution context!"
}