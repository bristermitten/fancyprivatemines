package me.bristermitten.fancyprivatemines.data

import me.bristermitten.fancyprivatemines.block.BlockMask
import java.util.*

data class PrivateMine(
        val owner: UUID,
        var open: Boolean = true,
        var blocks: BlockMask,
        var taxPercentage: Double
) {
}
