package me.bristermitten.fancyprivatemines.nms

import me.bristermitten.fancyprivatemines.block.BlockData
import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import org.bukkit.entity.Player

interface BlockPreviewService {
    fun setBlock(player: Player, location: ImmutableLocation, block: BlockData)

    fun setChunk(player: Player, chunkX: Int, chunkZ: Int, )
}
