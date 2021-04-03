package me.bristermitten.fancyprivatemines.command.subcommand

import kotlinx.coroutines.runBlocking
import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.command.mustBePlayer
import me.bristermitten.fancyprivatemines.lang.key.Info
import me.bristermitten.fancyprivatemines.mine.VoidWorldMineFactory
import me.bristermitten.fancyprivatemines.schematic.SchematicLoader
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateSubCommand(val plugin: FancyPrivateMines) : SubCommand(
    description = "Create a Mine",
    "fpm.create"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        sender.mustBePlayer()

        plugin.langComponent.message(sender, Info.GeneratingMine)
        val voidWorldMineFactory = VoidWorldMineFactory(plugin)
        val schematicFile = plugin.schematicsDir.resolve("paste1.schematic")
        val loader = SchematicLoader(plugin)

        val mine = runBlocking {
            voidWorldMineFactory.create(
                schematicFile,
                loader.loadSchematic(schematicFile),
                sender)
        }

        sender.teleport(mine.spawnLocation.toLocation())
    }
}
