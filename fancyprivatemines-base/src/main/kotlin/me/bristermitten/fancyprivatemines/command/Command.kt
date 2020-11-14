package me.bristermitten.fancyprivatemines.command

import me.bristermitten.fancyprivatemines.command.subcommand.SubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

open class Command : CommandExecutor {

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

    protected open fun CommandSender.sendUnknownCommand(cmd: String) {
        sendMessage("Unknown Command $cmd")
        sendHelp()
    }

    private fun CommandSender.sendHelp() {
        sendMessage(subCommandMap.entries.joinToString(separator = "\n") {
            "/fpm ${it.key} - ${it.value.description ?: ""}"
        })
    }
}
