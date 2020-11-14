package me.bristermitten.fancyprivatemines.block

interface BlockMask {
    fun generate(): BlockData

    fun generateBulk(amount: Int): List<BlockData>
}
