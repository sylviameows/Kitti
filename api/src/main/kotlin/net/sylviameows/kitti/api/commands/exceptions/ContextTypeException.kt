package net.sylviameows.kitti.api.commands.exceptions

class ContextTypeException(
    private val argument: String,
    private val expected: String,
    private val actual: String
) : Exception() {
    override val message: String
        get() = "Argument type of '$argument' is '$actual' and does not match your expected type '$expected'."
}