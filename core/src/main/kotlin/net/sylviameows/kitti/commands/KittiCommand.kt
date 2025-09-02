package net.sylviameows.kitti.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.sylviameows.kitti.Core
import net.sylviameows.kitti.api.commands.context.Context
import net.sylviameows.kitti.api.commands.Result
import net.sylviameows.kitti.api.commands.Command

class KittiCommand : Command<Core> {
    override val name: String = "kitti"

    override fun execute(context: Context<Core>): Result {
        val component = Component.empty().append(
            Component.text("ᴋɪᴛᴛɪ").color(TextColor.color(0xde9deb)),
            Component.space(),
            Component.text("v${Core.META.version}").color(NamedTextColor.WHITE),
            Component.space(),
            Component.text("- whiskering secrets into my plugins' ears.").color(NamedTextColor.GRAY)
        )

        context.source.sender.sendMessage(component)
        return Result.success()
    }
}