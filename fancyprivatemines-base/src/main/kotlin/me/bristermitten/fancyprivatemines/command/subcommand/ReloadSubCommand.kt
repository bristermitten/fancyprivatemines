package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import org.bukkit.command.CommandSender

class ReloadSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Reload FancyPrivateMines"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        plugin.reload()
    }
}
