package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.command.CommandRequirementNotSatisfiedException
import org.bukkit.command.CommandSender

abstract class SubCommand(val description: String?, val permission: String? = null) {

    @Throws(CommandRequirementNotSatisfiedException::class)
    abstract fun exec(sender: CommandSender, args: Array<String>)

    open fun tabComplete(sender: CommandSender, args: List<String>) : List<String> = emptyList()
}
