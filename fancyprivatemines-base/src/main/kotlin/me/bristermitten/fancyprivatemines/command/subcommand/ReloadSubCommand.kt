package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.lang.key.Commands
import org.bukkit.command.CommandSender

class ReloadSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Reload FancyPrivateMines",
        "fpm.reload"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        plugin.reload()
        plugin.langComponent.message(sender, Commands.RELOAD_DONE)
    }
}
