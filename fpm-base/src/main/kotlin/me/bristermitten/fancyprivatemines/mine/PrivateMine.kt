package me.bristermitten.fancyprivatemines.mine

import me.bristermitten.fancyprivatemines.block.BlockMask
import org.bukkit.Location
import java.util.*

data class PrivateMine(
        val owner: UUID,
        var open: Boolean = true,
        var blocks: BlockMask,
        var taxPercentage: Double,
        var spawnLocation: Location,
        var minLocation: Location,
        var maxLocation: Location,
)
