package me.bristermitten.fancyprivatemines.mine

import me.bristermitten.fancyprivatemines.schematic.MineSchematic
import org.bukkit.entity.Player
import java.io.File
import java.util.concurrent.CompletableFuture

abstract class MineFactory {

    abstract suspend fun create(schematicFile: File, mineSchematic: MineSchematic, owner: Player): PrivateMine
}
