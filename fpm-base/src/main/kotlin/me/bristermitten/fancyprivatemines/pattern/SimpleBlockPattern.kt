package me.bristermitten.fancyprivatemines.pattern

import kotlinx.serialization.Serializable
import me.bristermitten.fancyprivatemines.block.BlockData

@Serializable data class SimpleBlockPattern(val block: BlockData) : BlockPattern {
    override fun generate(): BlockData {
        return block
    }

    override fun generateBulk(amount: Int): List<BlockData> {
        return List(amount) { block }
    }
}
