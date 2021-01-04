package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.immutableCopy
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import java.util.*

private val faces = EnumSet.of(BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST)
val Block.isVisible
    get() = faces.any {
        getRelative(it).type == Material.AIR
    }

fun Sequence<Block>.filterVisible(): Sequence<Block> {
    val visibleBlocks = mutableSetOf<ImmutableLocation>()
    return filter { block ->
        val visible = faces.map(block::getRelative).any { relative ->
            val pos = relative.location.immutableCopy()
            if (pos in visibleBlocks) {
                visibleBlocks += pos
                return@filter true
            }
            relative.type == Material.AIR
        }
        if (visible) {
            visibleBlocks += block.location.immutableCopy()
        }
        visible
    }
}
