package me.bristermitten.fancyprivatemines.mine

import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.File
import java.util.concurrent.CompletableFuture

abstract class MineFactory {
    abstract fun create(schematic: File, owner: Player): CompletableFuture<PrivateMine>
}
