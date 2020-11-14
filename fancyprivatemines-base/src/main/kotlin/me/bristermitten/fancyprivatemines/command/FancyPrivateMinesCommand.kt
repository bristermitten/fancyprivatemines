package me.bristermitten.fancyprivatemines.command

import me.bristermitten.fancyprivatemines.command.subcommand.ListPrivateMinesSubCommand

class FancyPrivateMinesCommand : Command() {
    init {
        addSubCommand("list", ListPrivateMinesSubCommand())
    }
}
