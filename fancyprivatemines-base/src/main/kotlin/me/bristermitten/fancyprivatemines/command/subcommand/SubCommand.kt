package me.bristermitten.fancyprivatemines.command.subcommand

import org.bukkit.command.CommandSender

abstract class SubCommand(val description: String?, val permission: String? = null) {
    abstract fun exec(sender: CommandSender, args: Array<String>)
}
