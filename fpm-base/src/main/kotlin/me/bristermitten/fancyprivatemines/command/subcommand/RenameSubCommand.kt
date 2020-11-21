package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.command.lengthMustBeAtLeast
import me.bristermitten.fancyprivatemines.lang.key.Info
import me.bristermitten.fancyprivatemines.menu.MineMenu
import me.bristermitten.fancyprivatemines.mine.VoidWorldMineFactory
import me.bristermitten.fancyprivatemines.schematic.SchematicLoader
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RenameSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Rename a Mine",
        "fpm.rename"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        args.lengthMustBeAtLeast(1)

        TODO()
    }
}
