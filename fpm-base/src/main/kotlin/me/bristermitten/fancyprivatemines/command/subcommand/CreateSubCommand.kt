package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.lang.key.Info
import me.bristermitten.fancyprivatemines.mine.VoidWorldMineFactory
import me.bristermitten.fancyprivatemines.schematic.SchematicLoader
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Create a Mine",
        "fpm.create"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        plugin.langComponent.message(sender, Info.GeneratingMine)
        val start = System.currentTimeMillis().toDouble()
        val voidWorldMineFactory = VoidWorldMineFactory(plugin)
        val schematicFile = plugin.schematicsDir.resolve("paste1.schematic")
        val loader = SchematicLoader(plugin)
        val mine = voidWorldMineFactory.create(
                schematicFile,
                loader.loadSchematic(schematicFile),
                sender as Player)

        mine.whenComplete { t, u ->
            u?.printStackTrace()
            sender.teleport(t.spawnLocation)
            val took = System.currentTimeMillis() - start
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPasted in $took ms!"))
        }
    }
}
