package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.lang.key.Info
import me.bristermitten.fancyprivatemines.mine.VoidWorldMineFactory
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Create a Mine",
        "fpm.create"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        plugin.langComponent.message(sender, Info.GeneratingMine)
        val voidWorldMineFactory = VoidWorldMineFactory(plugin)
        val mine = voidWorldMineFactory.create(plugin.dataFolder.parentFile.resolve("WorldEdit").resolve("schematics").resolve("paste1.schematic"),
                sender as Player)

        mine.whenComplete { t, u ->
            u?.printStackTrace()
            sender.teleport(t.spawnLocation)
        }
    }
}
