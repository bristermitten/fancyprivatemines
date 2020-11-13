package me.bristermitten.fancyprivatemines.data.block

interface BlockMask {
    fun generate(): BlockData

    fun generateBulk(amount: Int): List<BlockData>
}
