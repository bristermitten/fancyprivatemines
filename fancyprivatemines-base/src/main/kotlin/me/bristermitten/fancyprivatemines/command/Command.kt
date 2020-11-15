package me.bristermitten.fancyprivatemines.command

import me.bristermitten.fancyprivatemines.command.subcommand.SubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

abstract class Command : TabExecutor {

    private val subCommandMap = mutableMapOf<String, SubCommand>()

    protected fun addSubCommand(alias: String, subCommand: SubCommand) {
        subCommandMap[alias.toLowerCase()] = subCommand
    }

    final override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendHelp()
            return true
        }

        val subCommand = subCommandMap[args[0].toLowerCase()]
        if (subCommand == null) {
            sender.sendUnknownCommand(args[0])
            return true
        }

        subCommand.exec(sender, args.drop(1).toTypedArray())
        return true
    }

    final override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        if (args.isEmpty()) {
            return subCommandMap.keys.toList()
        }
        if (args.size == 1) {
            return subCommandMap.keys.filter { it.startsWith(args[0]) }
        }
        return emptyList() //TODO
    }

    protected abstract fun CommandSender.sendUnknownCommand(cmd: String)

    private fun CommandSender.sendHelp() {
        sendMessage(subCommandMap.entries.joinToString(separator = "\n") {
            "/fpm ${it.key} - ${it.value.description ?: ""}"
        })
    }
}
