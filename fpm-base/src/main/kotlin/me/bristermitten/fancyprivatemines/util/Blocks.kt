package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import java.util.*

private val relative = setOf<(ImmutableLocation) -> ImmutableLocation>(
    { pos -> pos.add(1, 0, 0) }, //East
    { pos -> pos.add(-1, 0, 0) }, //West
    { pos -> pos.add(0, 1, 0) }, //Up
    { pos -> pos.add(0, -1, 0) }, //Down
    { pos -> pos.add(0, 0, 1) }, //North
    { pos -> pos.add(0, 0, -1) } //South
)

private val faces = EnumSet.of(
    BlockFace.EAST,
    BlockFace.WEST,
    BlockFace.UP,
    BlockFace.DOWN,
    BlockFace.NORTH,
    BlockFace.SOUTH,
)

val Block.isVisible
    get() = faces.any {
        getRelative(it).type == Material.AIR
    }

fun Sequence<ImmutableLocation>.filterVisible(): Sequence<ImmutableLocation> {
    val airBlocks = mutableSetOf<ImmutableLocation>()
    return filter { pos ->
        val block = pos.toLocation().block
        block.type == Material.AIR //If the block's type is air
    }.flatMap { pos -> //All the relative blocks are visible
        relative.map(pos::run)
    }
}
