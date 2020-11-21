package me.bristermitten.fancyprivatemines.command.subcommand

import me.bristermitten.fancyprivatemines.FancyPrivateMines
import me.bristermitten.fancyprivatemines.command.getPrivateMine
import me.bristermitten.fancyprivatemines.command.lengthMustBeAtLeast
import me.bristermitten.fancyprivatemines.lang.key.Commands
import org.bukkit.command.CommandSender

class RenameSubCommand(val plugin: FancyPrivateMines) : SubCommand(
        description = "Rename a Mine",
        "fpm.rename"
) {
    override fun exec(sender: CommandSender, args: Array<String>) {
        args.lengthMustBeAtLeast(2)

        val mine = args[0].getPrivateMine()
        val oldName = mine.name
        mine.name = args[1]

        plugin.langComponent.message(sender, Commands.RENAME_SUCCESSFUL,
                "%previous_name%", oldName ?: "<no name>",
                "%new_name%", args[1])
    }
}
