package me.bristermitten.fancyprivatemines.command

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.command.subcommand.CreateSubCommand
import me.bristermitten.fancyprivatemines.command.subcommand.ListPrivateMinesSubCommand
import me.bristermitten.fancyprivatemines.command.subcommand.ReloadSubCommand
import me.bristermitten.fancyprivatemines.lang.key.Commands
import me.bristermitten.fancyprivatemines.lang.key.Errors
import org.bukkit.command.CommandSender

class FancyPrivateMinesCommand(val plugin: FancyPrivateMines) : Command() {

    init {
        addSubCommand("list", ListPrivateMinesSubCommand())
        addSubCommand("reload", ReloadSubCommand(plugin))
        addSubCommand("create", CreateSubCommand(plugin))
    }

    override fun CommandSender.sendUnknownCommand(cmd: String) {
        plugin.langComponent.message(this, Errors.UnknownCommand)
    }

    override fun CommandSender.sendHelp() {
        plugin.langComponent.message(this, Commands.HELP_HEADER)

        subCommandMap.entries
                .filter { hasPermission(it.value.permission ?: "") }
                .forEach {
                    plugin.langComponent.message(this, Commands.HELP_COMMAND,
                            "%cmd_name%", it.key,
                            "%cmd_description%", it.value.description ?: "")
                }

        plugin.langComponent.message(this, Commands.HELP_FOOTER)
    }

    override fun CommandSender.sendNoPermission(cmd: String, permission: String) {
        plugin.langComponent.message(this, Errors.NoPermission,
                "%permission%", permission,
                "%cmd_name%", cmd)
    }
}
