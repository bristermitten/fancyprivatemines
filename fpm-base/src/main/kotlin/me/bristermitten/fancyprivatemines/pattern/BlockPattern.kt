package me.bristermitten.fancyprivatemines.pattern

import me.bristermitten.fancyprivatemines.block.BlockData


interface BlockPattern {
    fun generate(): BlockData

    fun generateBulk(amount: Int): List<BlockData>
}
