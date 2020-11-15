package me.bristermitten.fancyprivatemines.command.subcommand

import org.bukkit.command.CommandSender

class ListPrivateMinesSubCommand : SubCommand(
        description = "List all PrivateMines"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("All Private Mines:")
        sender.sendMessage("TODO")
    }
}
