package me.bristermitten.fancyprivatemines.command

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.command.subcommand.ListPrivateMinesSubCommand
import me.bristermitten.fancyprivatemines.command.subcommand.ReloadSubCommand
import me.bristermitten.fancyprivatemines.lang.key.Errors
import org.bukkit.command.CommandSender

class FancyPrivateMinesCommand(val plugin: FancyPrivateMines) : Command() {

    init {
        addSubCommand("list", ListPrivateMinesSubCommand())
        addSubCommand("reload", ReloadSubCommand(plugin))
    }

    override fun CommandSender.sendUnknownCommand(cmd: String) {
        plugin.langComponent.message(this, Errors.UnknownCommand)
    }
}
