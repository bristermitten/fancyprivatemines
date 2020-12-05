package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.command.getPrivateMine
import me.bristermitten.fancyprivatemines.command.lengthMustBeAtLeast
import me.bristermitten.fancyprivatemines.command.mustBePlayer
import me.bristermitten.fancyprivatemines.menu.MineMenu
import org.bukkit.command.CommandSender

class MenuSubCommand(val plugin: FancyPrivateMines) : SubCommand(
    description = "Create a Mine",
    "fpm.create"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        sender.mustBePlayer()
        args lengthMustBeAtLeast 1

        val mine = args[0].getPrivateMine()
        MineMenu(plugin).open(sender, mine)
    }


    override fun tabComplete(sender: CommandSender, args: List<String>): List<String> {
        val all = plugin.mineStorage.all.map { it.name ?: it.id.toString() }
        if (args.isEmpty()) {
            return all
        }

        return all.filter { it.startsWith(args[0]) }
    }
}
