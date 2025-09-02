package net.sylviameows.kitti.api.commands

import net.sylviameows.kitti.api.commands.context.Context
import org.bukkit.plugin.java.JavaPlugin

/**
 * Alias for using default Context in your commands, use RawCommand if using a custom Context instance.
 * @see RawCommand
 */
interface Command<Plugin : JavaPlugin> : RawCommand<Plugin, Context<Plugin>> {}