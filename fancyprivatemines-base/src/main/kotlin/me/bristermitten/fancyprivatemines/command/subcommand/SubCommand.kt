package me.bristermitten.fancyprivatemines.command.subcommand

import org.bukkit.command.CommandSender

abstract class SubCommand(val description: String?) {
    abstract fun exec(sender: CommandSender, args: Array<String>)
}
