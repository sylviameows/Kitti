package net.sylviameows.kitti.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.sylviameows.kitti.Core
import net.sylviameows.kitti.api.commands.Command
import net.sylviameows.kitti.api.commands.Context
import net.sylviameows.kitti.api.commands.Result
import org.bukkit.Bukkit

class KittiCommand : Command {
    override val name: String = "kitti"

    override fun execute(context: Context): Result {
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