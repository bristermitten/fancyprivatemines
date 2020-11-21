package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import org.bukkit.command.CommandSender

class ListPrivateMinesSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        "List all PrivateMines",
        "fpm.list"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("All Private Mines:")
        plugin.mineStorage.all.forEach {
            sender.sendMessage(it.id.toString() + " " + it.spawnLocation)
        }
    }
}
