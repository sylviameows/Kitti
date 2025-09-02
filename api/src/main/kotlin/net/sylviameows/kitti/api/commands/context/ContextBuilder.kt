package net.sylviameows.kitti.api.commands.context

interface ContextBuilder {
    fun set(key: String, value: Any): ContextBuilder
    fun subcommand(subcommand: String): ContextBuilder
}