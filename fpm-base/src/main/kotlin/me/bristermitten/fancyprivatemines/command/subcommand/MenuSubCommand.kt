package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.menu.MineMenu
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class MenuSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Create a Mine",
        "fpm.create"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        val uuid = UUID.fromString(args[0])
        val mine = plugin.mineStorage[uuid]!!
        MineMenu(plugin, sender as Player, mine).open()
    }
}
