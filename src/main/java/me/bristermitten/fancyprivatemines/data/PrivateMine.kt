package me.bristermitten.fancyprivatemines.data

import me.bristermitten.fancyprivatemines.data.block.BlockMask
import java.util.*

data class PrivateMine(
        val owner: UUID,
        var open: Boolean = true,
        var blocks: BlockMask
) {
}
